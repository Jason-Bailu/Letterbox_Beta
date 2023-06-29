const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: '/'
})

//配置 publicPath='/'，路由模式改为hash