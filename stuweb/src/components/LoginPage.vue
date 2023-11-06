<template>

    <div class="body">
      <div class="container-fluid my-container" id="large-header">
  
        <div class="box">
          <!-- logo -->
          <div class="logo">
  
          </div>
          <div class="form">
            <a-form
                :model="loginStu"
                name="basic"
                :label-col="{ style: { width: '120px' } }" :wrapper-col="{ span: 14 }"
                autocomplete="off"
                @finish="onFinish"
            >  
              <a-form-item
                  label="登录名"
                  name="username"
                  :rules="[{ required: true, message: '请输入登录名！' }]"
              >
                <a-input v-model:value="loginStu.username" />
              </a-form-item>
  
              <a-form-item
                  label="密码"
                  name="password"
                  :rules="[{ required: true, message: '请输入密码！' }]"
              >
                <a-input-password v-model:value="loginStu.password"  />
              </a-form-item>
  
              <a-form-item :wrapper-col="{ offset: 10, span: 15 }">
                <a-button type="primary" html-type="submit">登录</a-button>
              </a-form-item>
            </a-form>
          </div>
  
        </div>
      </div>
    </div>
  
  </template>
  
  <script>
  import {ref} from 'vue';
  import axios from "axios";
  import store from '@/store'
  import {message} from "ant-design-vue";
  export default {
    name: "LoginPage",
    setup() {
      const loginStu = ref({});

      //登录
      const onFinish = values=>{
        console.log(values);

        axios.get("api/loginStu",{
          params: {
            username: loginStu.value.username,
            password: loginStu.value.password
          }
        }).then(res =>{
          if (res.data.code == 200) {
            console.log("数据", res.data);

            //将user保存到vuex中
            store.commit("setStu", res.data.data[0]);
          }else {
            message.error(res.data.msg);
          }
        })
      }

      /*const load = ()=>{
        axios.get("api/selectLevel").then(res =>{
          console.log("数据",res.data.data);
        })
      }

      onMounted(()=>{
        load();
      })*/

      return {
        loginStu,
        onFinish
      };
    },
  }
  </script>
  
  <style scoped>
  .body {
    width: 100%;
    height: 100vh;
  }
  
  .my-container {
    height: 100vh;
    background-image: url("../../public/img/bg.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
  }
  
  .box {
    position: absolute;
    left: 420px;
    width: 460px;
    height: 380px;
    background-color: #ffffffaa;
    margin: 180px auto;
    border-radius: 5px;
    z-index: 999;
  }
  
  .logo {
    width: 100%;
    height: 80px;
    padding: 10px 5px 5px;
    background-image: url("../../public/img/head.png");
    background-size: 100% 100%;
  }
  .form {
    width: 100%;
    height: calc(440px - 80px);
    padding: 55px 56px 10px 10px;
    margin-left: 23px;
  }
  
  </style>