# Day1

## post和get请求的区别

- post传参需要使用@RequestBody进行，如果是特殊参数，使用map即可
- get传参需要使用@RequestParam进行，每个参数名和字段名一一对应

---

## 将用户登录的功能交给SpringSecurity+JWT来进行

1. 先实现JWT的token认证功能
2. 整合SpringSecurity的自定义配置
3. 完全替代原始login接口登录

---

## JWT工具类使用

- 注入依赖

  ```xml
  <!--jwt-->
  <dependency>
      <groupId>com.auth0</groupId>
      <artifactId>java-jwt</artifactId>
      <version>3.4.1</version>
  </dependency>
  <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>0.9.1</version>
  </dependency>
  ```

- JwtUtils——token工具类（生成token、验证token、根据token获取信息）

  ```java
  package com.dxy.letterboxbackstage.utils;
  
  public class JwtUtils {
      /**
       * 签发JWT
       * @param id
       * @param subject 可以是JSON数据 尽可能少
       * @param ttlMillis
       * @return
       */
      public static String createJWT(String id, String subject, long ttlMillis) {
          SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
          long nowMillis = System.currentTimeMillis();
          Date now = new Date(nowMillis);
          SecretKey secretKey = generalKey();
          JwtBuilder builder = Jwts.builder()
                  .setId(id)
                  .setSubject(subject)   // 主题
                  .setIssuer("dxy")     // 签发者
                  .setIssuedAt(now)      // 签发时间
                  .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
          if (ttlMillis >= 0) {
              long expMillis = nowMillis + ttlMillis;
              Date expDate = new Date(expMillis);
              builder.setExpiration(expDate); // 过期时间
          }
          return builder.compact();
      }
  
      /**
       * 生成jwt token
       * @param username
       * @return
       */
      public static String genJwtToken(String username){
          return createJWT(username,username,60*60*1000);
      }
  
      /**
       * 验证JWT
       * @param jwtStr
       * @return
       */
      public static CheckResult validateJWT(String jwtStr) {
          CheckResult checkResult = new CheckResult();
          Claims claims = null;
          try {
              claims = parseJWT(jwtStr);
              checkResult.setSuccess(true);
              checkResult.setClaims(claims);
          } catch (ExpiredJwtException e) {
              checkResult.setErrCode(JwtConstant.JWT_ERRCODE_EXPIRE);
              checkResult.setSuccess(false);
          } catch (SignatureException e) {
              checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
              checkResult.setSuccess(false);
          } catch (Exception e) {
              checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
              checkResult.setSuccess(false);
          }
          return checkResult;
      }
  
      /**
       * 生成加密Key
       * @return
       */
      public static SecretKey generalKey() {
          byte[] encodedKey = Base64.decode(JwtConstant.JWT_SECERT);
          SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
          return key;
      }
  
  
      /**
       * 解析JWT字符串
       * @param jwt
       * @return
       * @throws Exception
       */
      public static Claims parseJWT(String jwt) {
          SecretKey secretKey = generalKey();
          return Jwts.parser()
                  .setSigningKey(secretKey)
                  .parseClaimsJws(jwt)
                  .getBody();
      }
  }
  ```

- CheckResult——token验证的相关信息

  ```java
  package com.dxy.letterboxbackstage.common;
  
  import io.jsonwebtoken.Claims;
  
  public class CheckResult {
  
      private int errCode;
  
      private boolean success;
  
      private Claims claims;
  
      public int getErrCode() {
          return errCode;
      }
  
      public void setErrCode(int errCode) {
          this.errCode = errCode;
      }
  
      public boolean isSuccess() {
          return success;
      }
  
      public void setSuccess(boolean success) {
          this.success = success;
      }
  
      public Claims getClaims() {
          return claims;
      }
  
      public void setClaims(Claims claims) {
          this.claims = claims;
      }
  
  }
  ```

- JwtConstant——关于token认证的常量配置

  ```java
  package com.dxy.letterboxbackstage.common.constant;
  
  public class JwtConstant {
      /**
       * token
       */
      public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
      public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
      public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过
  
      /**
       * JWT
       */
      public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
      public static final long JWT_TTL = 24*60 * 60 * 1000;									//token有效时间
  }
  
  ```

- JwtAuthenticationFilter

  ```java
  package com.dxy.letterboxbackstage.config.filter;
  
  /**
   * @Author: JasonD
   * @date: 2023/5/20 10:30
   * @Description: jwt认证过滤器
   */
  public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
      @Autowired
      UserServiceImpl userService;
  
      @Autowired
      MyUserDetailsServiceImpl userDetailsService;
  
      private static final String[] URL_WHITELIST = {
              "/login",
              "/logout",
              "/file/download/**",
              "/user/register"
      };
  
      public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
          super(authenticationManager);
      }
  
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
          //获取header中的token
          String token = request.getHeader("Authorization");
          System.out.println("请求的URL：" + request.getRequestURI());
  
          //如果token为空，或者请求的地址为白名单地址直接放行
          if (StrUtil.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())) {
              chain.doFilter(request, response);
              return;
          }
  
          //如果token不为空，则需要验证
          CheckResult checkResult = JwtUtils.validateJWT(token);
          if (!checkResult.isSuccess()) {
              switch (checkResult.getErrCode()) {
                  case JwtConstant.JWT_ERRCODE_NULL:
                      throw new JwtException("token不存在");
                  case JwtConstant.JWT_ERRCODE_EXPIRE:
                      throw new JwtException("token已过期");
                  case JwtConstant.JWT_ERRCODE_FAIL:
                      throw new JwtException("token验证不通过");
              }
          }
  
          Claims claims = JwtUtils.parseJWT(token);
          String username = claims.getSubject();
          User user = userService.getByUsername(username);
          //将登录后的用户信息保存到threadlocal
          UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
          UserThreadLocal.set(userVo);
          //设置token认证和授权
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetailsService.getUserAuthority());
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          chain.doFilter(request, response);
      }
  }
  ```

- JwtAuthenticationEntryPoint

  ```java
  package com.dxy.letterboxbackstage.config.filter;
  
  /**
   * @Author: JasonD
   * @date: 2023/5/20 11:01
   * @Description: jwt认证异常处理器
   */
  @Component
  public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
          response.setContentType("application/json;charset=UTF-8");
          ServletOutputStream outputStream = response.getOutputStream();
          outputStream.write(JSONUtil.toJsonStr(Result.error(CodeEnum.C401, "认证失败请登录")).getBytes());
          outputStream.flush();
          outputStream.close();
      }
  }
  ```

- **配置好后相关的JWT资源后，进行登录页面的token生成以及token获取保存到本地cookies中（具体见vue代码中的auth.js），在前端的所有请求中都要带上对应的token才能进行访问**

- **JWT的使用属于token的认证过滤，后面要整合进Security进行进一步认证和权限的统一设置和管理，当整合Security后，Controller中的login接口就不用在继续使用，完全交给Security进行管理**

---

## SpringSecurity配置类

- 注入依赖

  ```xml
  <!--security-->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  ```
  
- 配置SecurityConfig类——这里使用的Security版本基于Springboot2.7整合

  - 主要的配置在于：
    1. **获取登录用户的信息**
    2. 开启跨域、关闭csrf攻击（跨站请求伪造）
    3. **自定义登录登出处理器**
    4. session禁止配置
    5. **白名单以及拦截配置**
    6. **自定义配置整合JWT**


  ```java
  package com.dxy.letterboxbackstage.config;
  
  /**
   * @Author: JasonD
   * @date: 2023/5/19 23:16
   * @Description:
   */
  @Configuration
  @EnableWebSecurity
  @EnableGlobalMethodSecurity(prePostEnabled = true)//用于开启security相关注解
  public class SecurityConfig {
      private static final String[] URL_WHITELIST = {
              "/login",
              "/logout",
              "/file/download/**",
              "/user/register"
      };
  
      @Autowired
      private LoginSuccessHandler loginSuccessHandler;
  
      @Autowired
      private LoginFailureHandler loginFailureHandler;
  
      @Autowired
      private LogoutHandler logoutHandler;
  
      @Autowired
      private MyUserDetailsServiceImpl userDetailsService;
  
      @Autowired
      private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  
      //编码器 整合了SpringSecurity后的密码都要进行加密处理
      @Bean
      public BCryptPasswordEncoder bCryptPasswordEncoder() {
          return new BCryptPasswordEncoder();
      }
  
      @Bean
      JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
          JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authentication -> {return authentication;});
          return jwtAuthenticationFilter;
      }
  
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
          //开启从库中获取用户信息的配置
          httpSecurity.userDetailsService(userDetailsService);
  
          //开启跨域 以及csrf攻击关闭（csrf伪造攻击）
          httpSecurity
              .cors()
          .and()
              .csrf().disable()
  
          //自定义登录登出配置
          .formLogin()
              .successHandler(loginSuccessHandler)
              .failureHandler(loginFailureHandler)
          .and()
              .logout().logoutSuccessHandler(logoutHandler)
  
          //session禁止配置
          .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  
          //拦截配置
          .and()
              .authorizeRequests()
              .antMatchers(URL_WHITELIST).permitAll() //白名单访问
              .anyRequest().authenticated() //其他需要认证
  
          //自定义异常处理配置，这里整合了JWT认证异常处理器
          .and()
              .exceptionHandling()
              .authenticationEntryPoint(jwtAuthenticationEntryPoint)
  
          //自定义配置，这里就是将jwt整合整合到secruity中，实现自定义配置
          .and()
              .addFilter(jwtAuthenticationFilter()); //添加jwt认证过滤器
  
          return httpSecurity.build();
      }
  }
  ```

- 登录用户信息获取

  ```java
  /**
   * @Author: JasonD
   * @date: 2023/5/20 09:39
   * @Description: 自定义UserDetails
   */
  @Service
  public class MyUserDetailsServiceImpl implements UserDetailsService {
      @Autowired
      private UserServiceImpl userService;
  
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userService.getByUsername(username);
          if (user == null) {
              throw new UsernameNotFoundException("用户名或密码错误");
          } else if (!user.getEnable()) {
            	//这里使用了一个自定义异常类，通过全局异常处理器进行统一处理
              throw new UserLockedException("用户已被禁用，请联系管理员");
          }
        	//返回Security的用户类，其中Authority在后面授权功能进行实现
          return new org.springframework.security.core.userdetails.User(user.getUsername(),
                  user.getPassword(), getUserAuthority());
      }
  		
    	//其中Authority在后面授权功能进行实现
      public List<GrantedAuthority> getUserAuthority() {
          return new ArrayList<>();
      }
  }
  ```

- 登录认证实现（handler）

  - LoginSuccessHandler登录成功处理器

    ```java
    /**
     * @Author: JasonD
     * @date: 2023/5/19 23:32
     * @Description: 登录成功处理器
     */
    @Component
    public class LoginSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            String username = "user";
          	//根据登录成功的用户名来生成对应的token，签发一定时效的token
            String token = JwtUtils.genJwtToken(username);
            outputStream.write(JSONUtil.toJsonStr(Result.success("登录成功", token)).getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
    ```

  - LoginFailureHandler登录失败处理器

    ```java
    /**
     * @Author: JasonD
     * @date: 2023/5/19 23:33
     * @Description: 登录失败处理器
     */
    @Component
    public class LoginFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSONUtil.toJsonStr(Result.error(CodeEnum.C401, "用户名或密码错误")).getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
    ```

  - LogoutHandler登出处理器

    ```java
    /**
     * @Author: JasonD
     * @date: 2023/5/20 11:06
     * @Description: 自定义logout处理器
     */
    @Component
    public class LogoutHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
          	//这里登出清除threadlocal保存的用户信息
            UserThreadLocal.remove();
            outputStream.write(JSONUtil.toJsonStr(Result.success("退出成功", null)).getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
    ```

- 由于前端有时候也要获取登录用户的相关信息，这里采用了ThreadLocal类保存登录用户的信息，在登录成功时保存，在登出时进行清除

  - UserThreadLocal

    ```java
    package com.dxy.letterboxbackstage.utils;
    
    import com.dxy.letterboxbackstage.vo.UserVo;
    
    /**
     * @Author: JasonD
     * @date: 2023/5/21 21:08
     * @Description: ThreadLocal类用于保存当前登录的用户信息
     */
    public class UserThreadLocal {
        private UserThreadLocal() {}
    
        private static final ThreadLocal<UserVo> THREAD_LOCAL = new ThreadLocal<>();
    
        public static void set(UserVo userVo) {
            THREAD_LOCAL.set(userVo);
        }
    
        public static UserVo get() {
            return THREAD_LOCAL.get();
        }
    
        public static void remove() {
            THREAD_LOCAL.remove();
        }
    }
    ```