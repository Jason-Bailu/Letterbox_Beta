# Letterbox_Beta
Letterbox for letter admin

#### 介绍
信件管理

#### 技术栈
主要：后端：Java11、Spring Boot2、Jwt、Spring Security、Mysql、Redis、OSS、Swagger、Lombok、mybatis-plus、
     前端：Vue2、Vue-router、Vuex、JSEncrypt、Axios、Element-ui、Html、Js、Css
其他：Docker、云服务器相关配置（Mysql、Redis、Nginx）

#### 安装教程

1.  将数据库sql文件进行导入
2.  修改前端访问ip地址以及port端口号、加密密钥
3.  修改后端配置类（ip地址以及port端口号，数据库配置，OSS api配置）

#### 使用说明

1.  mvn打包后端项目jar包
2.  npm打包前端项目dist
3.  将打包的项目上传到你的服务器内（前提是配置好对应的数据库环境，包括java11、redis、nginx、mysql）
