# Day3

## 动态路由设置

- vue2.6.14+vue-router3.2.0+vuex3.6.2

- store.js 保存了权限菜单的内容，通过获取权限菜单的内容来设置路由

  ```javascript
  export default new Vuex.Store({
    state: {
    },
    getters: {
      GET_MENUS: state => {
        return JSON.parse(sessionStorage.getItem("menus"));
      }
    },
    mutations: {
      set_menus(state, menus) {
        sessionStorage.setItem("menus", JSON.stringify(menus));
      },
      set_routes_state(state, hasRoutes) {
        state.hasRoutes = hasRoutes
      },
      logout() {
        sessionStorage.clear();
        delToken();
        router.push("/login");
      }
    },
    actions: {
    },
    modules: {
    }
  })
  ```

- router.js 设置动态路由方法，在登录中进行调用

  ```javascript
  export const setRoutes = () => {
    const menus = store.getters.GET_MENUS;
    if (menus) {
      const indexMenu = [{path: '/', name: '首页', component: MainView, children: []}];
      menus.forEach(pmenu => {
        pmenu.children.forEach(menu => {
          let route = {
            path: menu.path,
            name: menu.menuName,
            component: () => import("../views/"+menu.component+".vue")
          }
          indexMenu[0].children.push(route);
        })
      })
      router.addRoutes(indexMenu);
    }
  }
  ```

- **这里存在一个bug，浏览器刷新后，路由被重置了，无法显示页面**

  路由bug修复，页面刷新会重置vuex保存的数据，所有需要在路由守卫这里进行判断，是否需要重新添加路由信息，保持页面正常刷新

  ```js
  state: {
    //该变量用于判断页面是否刷新
    isAddRoutes: false
  },
  ```
  
  在动态路由设置方法里面改变改变量isAddRoutes=true：**store.state.isAddRoutes = true;**
  
  ```js
  export const setRoutes = () => {
    const menus = store.getters.GET_MENUS;
    if (menus) {
      const indexMenu = [{path: '/', name: '首页', redirect: '/home', component: MainView, children: []}
    ];
      menus.forEach(pmenu => {
        pmenu.children.forEach(menu => {
          let route = {
            path: menu.path,
            name: menu.menuName,
            component: () => import("../views/"+menu.component+".vue")
          }
          indexMenu[0].children.push(route);
        })
      })
      router.addRoutes(indexMenu);
      store.state.isAddRoutes = true;
    }
  }
  ```
  
  路由守卫
  
  ```js
  router.beforeEach( async (to, from, next) => {
    const whiteList = ['/login', '/register'];
    let token = getToken();
    if (token) {
      //已经登录后直接跳转到主页
      if (to.path === '/login') {
        next({path: '/'})
      }
      //刷新后，store中重置，需要重新添加路由
      if (!store.state.isAddRoutes) {
        setRoutes();
        next({...to, replace: true});
      } else {
        //如果没刷新直接跳转
        next();
      }
    } else {
      	//白名单直接跳转
        if (whiteList.includes(to.path)) {
          next();
        } else {
          //没有登录跳转到登录页面
          next('/login');
        }
    }
  })
  ```


---

## 项目部署

- 云服务器环境配置（docker+nginx）

- 前端部署
  1. 项目配置打包：将localhost改为部署服务器地址，进行打包，vue打包后文件为dist

     注意router的路由模式hash还是history，以及配置中路径默认为publicPath: '/'

  2. docker配置mysql

     **这里要注意的是docker运行后台容器的话，需要先运行一个容器实例，将内部一些数据和配置文件进行拷贝到宿主机路径下**：1是为了后面进行后台运行，2是为了方便后面进行容器数据卷映射到宿主机上方便进一步操作（包括配置修改、数据导入导出）

     ```bash
     docker run -p 3306:3306 --privileged=true -v /home/mysql/data:/var/lib/mysql  --name mysql -e MYSQL_ROOT_PASSWORD=xxxxxx -d mysql
     ```

  3. docker部署redis

     ```bash
     docker run -p 6379:6379 --name redis 别名
     --privileged=true  容器卷权限
     -v /docker/redis/redis.conf[宿主机配置文件]:/etc/redis/redis.conf [容器配置文件]
     -v /docker/redis/data[宿主机数据存储位置]:/data [容器数据存储位置]
     -d redis[:版本号]
     ```

  4. docker部署nginx

     ```bash
     docker run -d \ 
     --name nginx \
     -v /home/nginx/conf.d:/etc/nginx/conf.d \ (以下是将/home/nginx的内容挂载到对应的目录下，注意位置千万不能搞错)
     -v /home/nginx/html:/etc/nginx/html \
     -v /home/nginx/log:/usr/log/nginx \
     -p 8080:80 \
     nginx
     ```

     其中html文件夹就可以放置我们的前端项目，将前端打包后dist文件夹中的文件拷贝到宿主机上html文件夹内即可

  5. 通过http://服务器公网ip:端口号/login进行访问即可

- 后端部署

  1. 修改相关配置信息，主要是设置数据库的访问url，包括mysql以及redis
  2. 打包成jar包，放到宿主机上进行后台运行即可，后台运行命令nohup java -jar XXX.jar &

---

- **bug：nginx页面刷新404** 下面这个方法不太管用，后面直接将路由模式改为hash，可以解决很多问题，在线等大佬指导

    ```bash
    location / {
        root   /usr/share/nginx/html/;
        index  index.html index.htm;
        #需要指向下面的@router否则会出现vue的路由在nginx中刷新出现404
        try_files $uri $uri/ @router;
    }
    
    #对应上面的@router，主要原因是路由的路径资源并不是一个真实的路径，所以无法找到具体的文件
    #因此需要rewrite到index.html中，然后交给路由在处理请求资源
    location @router {
        rewrite ^.*$ /index.html last;
    }
    ```
    
    