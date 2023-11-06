<template>
  <a-modal v-model:open="open" title="Basic Modal" @ok="handleOk">
    确定要退出吗？
  </a-modal>
</template>

<script>
import axios from "axios";
import store from "@/store";
import {computed, ref} from "vue";
import {message} from "ant-design-vue";

export default {
  name: "LoginOut",
  setup(){
    //获取登录信息
    const loginStu = computed(()=>{
      return store.state.stu;
    })

    //对话框的开闭情况
    const open = ref(true);

    //点击对话框确认按钮后触发
    const handleOk = ()=> {
      //退出登录
      axios.get("api/stuLoginOut/" + loginStu.value.token).then(res => {
        if (res.data.code == 200) {
          store.commit("setStu", {});
          message.success("退出成功");
        } else {
          message.error("退出失败");
        }
      })
    }
    return{
      open,
      handleOk
    }
  }
}
</script>

<style scoped>

</style>