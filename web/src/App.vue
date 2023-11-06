<template>
  <LoginPage v-if="!loginUser.id"></LoginPage>

  <a-layout v-if="!!loginUser.id">
    <a-layout-header class="header" style="background-color: cornflowerblue">
      <div class="logo" style="float: left">
        <img :src="imagePath" alt="logo">
      </div>
      <div style="background-color: chartreuse; border-radius: 50%; float: right; margin-right: 50px">
        <a-dropdown>
          <a class="ant-dropdown-link" @click.prevent>
            {{loginUser.username}}
            <DownOutlined />
          </a>
          <template #overlay>
            <a-menu>
              <a-menu-item>
                <a @click="loginOut">退出登录</a>
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>
    </a-layout-header>

    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            v-model:selectedKeys="selectedKeys2"
            v-model:openKeys="openKeys"
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined />
                系统管理
              </span>
            </template>
            <a-menu-item key="1">
              <router-link to="/user">用户管理</router-link>
            </a-menu-item>

          </a-sub-menu>

          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined />
                老师管理
              </span>
            </template>
            <a-menu-item key="2">
              <router-link to="/teacher">老师列表</router-link>
            </a-menu-item>
            <a-menu-item key="3">
              <router-link to="/readPaper">老师阅卷</router-link>
            </a-menu-item>

          </a-sub-menu>

          <a-sub-menu key="sub3">
            <template #title>
              <span>
                <notification-outlined />
                学生管理
              </span>
            </template>
            <a-menu-item key="4">
              <router-link to="/student">学生列表</router-link>
            </a-menu-item>

          </a-sub-menu>

          <a-sub-menu key="sub4">
            <template #title>
              <span>
                <notification-outlined />
                试卷管理
              </span>
            </template>
            <a-menu-item key="5">
              <router-link to="/paper">试卷列表</router-link>
            </a-menu-item>
            <a-menu-item key="6">
              <router-link to="/level">试卷等级</router-link>
            </a-menu-item>
            <a-menu-item key="7">
              <router-link to="/zujuan">试卷组卷</router-link>
            </a-menu-item>
          </a-sub-menu>

          <a-sub-menu key="sub5">
            <template #title>
              <span>
                <notification-outlined />
                题库管理
              </span>
            </template>
            <a-menu-item key="8">
              <router-link to="/question">题库列表</router-link>
            </a-menu-item>

          </a-sub-menu>

        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
<!--        <a-breadcrumb style="margin: 16px 0">
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
        </a-breadcrumb>-->
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import {computed, defineComponent, ref} from 'vue';
import LoginPage from "@/components/LoginPage";
import store from '@/store'
import axios from "axios";
import {message} from "ant-design-vue";
export default defineComponent({
  components: {
    LoginPage,
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
  },

  data(){
    return{
      imagePath:require('../public/img/head.png')
    }
  },

  setup() {
    //获取登录信息
    const loginUser = computed(()=>{
      return store.state.user;
    })

    //退出登录
    const loginOut = ()=>{
      //发送请求
      axios.get("api/loginOut/"+loginUser.value.toKen).then(res =>{
        if (res.data.code == 200){
          store.commit("setUser",{});
          message.success("退出成功");
        }else {
          message.error("退出失败");
        }
      })
    }

    return {
      //selectedKeys: ref(['1']),
      //selectedKeys2: ref(['1']),
      collapsed: ref(false),
      //openKeys: ref(['sub1']),
      loginUser,
      loginOut
    };
  },
});
</script>
<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

img{
  height: 65px;
}
.site-layout-background {
  background: #fff;
}
</style>