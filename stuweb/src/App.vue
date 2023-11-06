<template>
  <LoginPage v-if="!loginStu.id"></LoginPage>

  <a-layout class="layout" v-if="!!loginStu.id">
    <a-layout-header>
      <div class="logo" />
      <a-menu
          v-model:selectedKeys="selectedKeys"
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">
            <router-link to="/paperList">首页</router-link>
        </a-menu-item>
        <a-menu-item key="2">
          <router-link to="/stuScore">分数</router-link>
        </a-menu-item>
        <a-menu-item key="3">
          <router-link to="/loginOut">退出</router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content style="padding: 0 50px">
<!--      <a-breadcrumb style="margin: 16px 0">

      </a-breadcrumb>-->
      <div :style="{ background: '#fff', padding: '24px', minHeight: 'calc(100vh - 130px)' }">
        <router-view />
      </div>
    </a-layout-content>
    <a-layout-footer style="text-align: center">
      Ant Design ©2018 Created by Ant UED
    </a-layout-footer>
  </a-layout>
</template>

<script>

import {computed} from "vue";
import LoginPage from "@/components/LoginPage";
import store from '@/store'

export default {
  components: {LoginPage},
  setup(){
    const loginStu = computed(()=>{
      return store.state.stu;
    })

    return{
      loginStu
    }
  }
}
</script>

<style scoped>
.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #fff;
}
#components-layout-demo-top .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}
.ant-row-rtl #components-layout-demo-top .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

[data-theme='dark'] .site-layout-content {
  background: #141414;
}
</style>