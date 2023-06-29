# Day4

## Redis的使用

### 引入依赖

```xml
<!--redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

### Redis配置

- application.yml

  ```yaml
  spring: 
  	  redis:
        host: 主机ip
        port: redis端口号
        database: 0 默认数据库0
        connect-timeout: 1800000
        lettuce:
          pool:
            max-active: 20
            max-wait: -1
            max-idle: 5
            min-idle: 0
  ```

- RedisConfig

  ```java
  @EnableCaching
  @Configuration
  public class RedisConfig extends CachingConfigurerSupport {
      @Bean
      public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
          RedisTemplate<String, Object> template = new RedisTemplate<>();
          RedisSerializer<String> redisSerializer = new StringRedisSerializer();
          Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
          ObjectMapper om = new ObjectMapper();
          om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
          om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
          jackson2JsonRedisSerializer.setObjectMapper(om);
          template.setConnectionFactory(factory);
          //key序列化
          template.setKeySerializer(redisSerializer);
          //value序列化
          template.setValueSerializer(jackson2JsonRedisSerializer);
          //value hashmap序列化
          template.setHashKeySerializer(jackson2JsonRedisSerializer);
          return template;
      }
  
      @Bean
      public CacheManager cacheManager(RedisConnectionFactory factory) {
          RedisSerializer<String> redisSerializer = new StringRedisSerializer();
          Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
          //解决查询缓存转换异常的问题
          ObjectMapper om = new ObjectMapper();
          om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
          om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
          jackson2JsonRedisSerializer.setObjectMapper(om);
          //配置序列化（解决乱码问题），过期时间600秒
          RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                  .entryTtl(Duration.ofSeconds(600))
                  .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                  .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                  .disableCachingNullValues();
          RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                  .cacheDefaults(config)
                  .build();
          return cacheManager;
      }
  
      //Redis消息监听器
      @Bean
      public RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                                     RedisMessageListener listener) {
          final String CHANNEL_SESSION = "heartbeat";//订阅心跳频道
          RedisMessageListenerContainer container = new RedisMessageListenerContainer();
          //监听所有库的key过期事件
          container.setConnectionFactory(factory);
          //所有订阅消息，都需要在这里进行注册绑定
          //可以添加多个messageListener，配置不同的通道
          container.addMessageListener(listener, new PatternTopic(CHANNEL_SESSION));
          /**
           * 设置序列化对象
           * 特别注意：1. 发布的时候需要设置序列化；订阅方也需要设置序列化
           *         2. 设置序列化对象必须放在[加入消息监听器]这一步后面，否则会导致接收器接收不到消息
           */
          Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
          ObjectMapper om = new ObjectMapper();
          om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
          om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
          jackson2JsonRedisSerializer.setObjectMapper(om);
          container.setTopicSerializer(jackson2JsonRedisSerializer);
          return container;
      }
  }
  ```

- RedisMessageListener

  ```java
  @Component
  public class RedisMessageListener implements MessageListener {
      @Autowired
      private RedisTemplate redisTemplate;
  
      @Override
      public void onMessage(Message message, byte[] pattern) {
          System.out.println("channel: " + new String(pattern));
          String context = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
          System.out.println(context);
      }
  }
  ```

### Redis缓存使用（RedisUtils）

```java
@Component
public class RedisUtils {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 发布消息
     */
    public boolean converAndSend(String channel, String message) {
        if (!StrUtil.hasLetter(channel)) {
            return false;
        }
        try {
            redisTemplate.convertAndSend(channel, message);
            System.out.println("send: " + message);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis订阅异常");
        }
    }

    /**
     * 添加key
     */
    public boolean set(String key, Object data, Integer expireTime) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, data, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis添加key异常");
        }
    }

    /**
     * 更新key
     */
    public boolean getAndExpire(String key, Integer expireTime) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().getAndExpire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis更新key异常");
        }
        return false;
    }

    /**
     * 获取多个key
     */
    public Set<String> getMultiKeys(String keyPrefix) {
        Set<String> set = redisTemplate.keys(keyPrefix+"*");
        return set;
    }

    /**
     * 获取key的时间 -1就是没有过期时间，-2没有该key
     */
    public long getTTL(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     */
    public boolean getAndDelete(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            throw new ServiceExecption(CodeEnum.C500, "Redis删除key异常");
        }
    }
}
```

### Session会话管理（SessionUtils）

```java
@Component
public class SessionUtils {
    private static final String SESSION_PREFIX = "session:";
    private static final int SESSION_EXPIRY = 1800; //会话过期时间 单位秒 设置为半个小时
    private static final String HEARTBEAT_CHANNEL = "heartbeat";

    @Autowired
    RedisUtils redisUtils;

    // 创建会话
    public void createSession(String username) {
        String sessionKey = SESSION_PREFIX + username;
        redisUtils.set(sessionKey, username, SESSION_EXPIRY);
        System.out.println("createSession");
    }

    // 发送心跳 刷新会话key
    public void sendHeartbeat(String username) {
        String sessionKey = SESSION_PREFIX + username;
        redisUtils.getAndExpire(sessionKey, SESSION_EXPIRY);
        redisUtils.converAndSend(HEARTBEAT_CHANNEL, sessionKey);//发送心跳
        System.out.println("sendHeartbeat");
    }

    // schedule定时任务用于检查检查断开死连接会话
    public void checkAndDisconnectDeadSessions() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        for (String sessionKey : sessionKeys) {
            long remainingTime = redisUtils.getTTL(sessionKey);
            if (remainingTime == -2) {
                //会话已过期
                String username = sessionKey.substring(SESSION_PREFIX.length());
                System.out.println("Disconnecting dead session: " + username);
            }
        }
    }

    // 查看当前所有sessionkeys状态
    public void getAllKey() {
        Set<String> sessionKeys = redisUtils.getMultiKeys(SESSION_PREFIX + "*");
        sessionKeys.forEach(key -> {
            System.out.println(key);
            System.out.println(redisUtils.getTTL(key));
        });
    }

    //查看制定key状态
    public Boolean getKey(String username) {
        long ttl = redisUtils.getTTL(SESSION_PREFIX + username);
        if (ttl == -2) {
            return false;
        }
        return true;
    }
}
```

## 会话管理配置

### 在用户登录的时候直接放行

- UserServiceImpl

  ```java
  public User getByUsername(String username) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username).eq("enable", 1);
      User user = userMapper.selectOne(wrapper);
      if (user == null) {
        	//直接抛出异常交给JwtAuthenticationEntryPoint处理返回
          throw new UserLockedException("用户已被禁止，请联系管理员");
      }
      return user;
  }
  ```

- 用户登录只需要检查是否被禁用
  - 被禁用直接抛出禁用异常
  - 未禁用直接放行进行访问

### 用户登录成功后保存当前会话session到Redis中

- LoginSuccessHandler

  ```java
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
  	...
    //创建新会话
    sessionUtils.createSession(username);
    ...
  }
  ```

- 将当前用户的用户名作为session的内容保存到Redis中
  - Key = session: username
  - data = username
  - 失效时间：30min

### 同时利用Timer类开启定时检查会话状态

- LoginSuccessHandler

  ```java
  //开启定时任务
  Timer timer = new Timer(username);
  timer.schedule(new TimerTask() {
      @Override
      public void run() {
          sessionUtils.checkAndDisconnectDeadSessions();
          sessionUtils.getAllKey();
      }
  }, 90000, 90000);
  ```

- 定时每90秒检查一次，将失效session进行key删除

### 在每个访问的同时进行Redis中Session更新

- JwtAuthenticationFilter

  ```java
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JwtException {
    ...
    String username = claims.getSubject();
    if (!sessionUtils.getKey(username)) {
        // 如果有无该用户session记录就抛出异常
        throw new UserExpiredException("用户已登出，请重新登录");
    } else {
        // 如果有该用户session记录就发送心跳进行session更新
        sessionUtils.sendHeartbeat(username);
    }
    ...
  }
  ```

- 用户长时间不操作后，session过期，在下一次访问的时候进行redis的检查session
- 检查session已失效——>抛出失效异常 UserExpiredException("用户已登出，请重新登录")
- 检查session未失效——>放行，同时刷新session sessionUtils.sendHeartbeat(username)