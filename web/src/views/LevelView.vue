<template>
  <a-table :dataSource="dataSource" :columns="columns" />
</template>
<script>


import {defineComponent, onMounted, ref} from "vue";
import axios from "axios";

const columns = [
  {
    title: 'id',
    dataIndex: 'id'
  },{
    title: '难度',
    dataIndex: 'name'
  }
];

export default defineComponent({
  setup() {
    //后端查询到的试卷难度
    const dataSource = ref([]);

    //查询
    const load = ()=>{
      axios.get("api/selectLevel").then(res =>{
        dataSource.value = res.data.data;
      })
    }

    //周期函数 挂载时调用
    onMounted(()=>{
      load();
    })

    return {
      columns,
      dataSource
    };
  },
});
</script>