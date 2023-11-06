import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import {isEmpty} from "@/util/Tool";
import axios from "axios";

createApp(App).use(store).use(Antd).use(router).mount('#app')

//axios拦截器
//请求拦截器 保存token到请求头
axios.interceptors.request.use(
    (config)=>{
        console.log("config",config);

        //在请求头里保存token
        const token = store.state.stu.token;
        //console.log("stu",store.state.stu);
        console.log("token",token);

        //判断是否为空
        if (!isEmpty(token)){
            //有值
            config.headers.toKen = token;
        }

        return config;
    }
)