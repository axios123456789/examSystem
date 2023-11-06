import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from "axios";
import {isEmpty} from "@/util/Tool";

createApp(App).use(store).use(router).use(Antd).mount('#app')

//axios拦截器
//请求拦截器 保存token到请求头
axios.interceptors.request.use(
    (config)=>{
        console.log("config",config);

        //在请求头里保存token
        const token = store.state.user.toKen;
        console.log("token",token);

        //判断是否为空
        if (!isEmpty(token)){
            //有值
            config.headers.toKen = token;
        }

        return config;
    }
)