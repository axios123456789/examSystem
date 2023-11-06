const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,  //关闭严格语法检查
  devServer:{
    proxy:{
      "/api":{
        target:"http://localhost:8082/", //代理的目标地址 即后端地址
        changeOrigin:true,  //配置同源
        pathRewrite:{ //路径重写
          '/api':''
        }
      }
    }
  }
})
