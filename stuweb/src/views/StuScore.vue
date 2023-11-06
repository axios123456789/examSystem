<template>
  <a-table bordered :data-source="dataSource" :columns="columns"></a-table>
</template>

<script>

import {computed, onMounted, ref} from "vue";
import store from "@/store";
import axios from "axios";

const columns = [{
  title: '姓名',
  dataIndex: 'username'
}, {
  title: '试卷',
  dataIndex: 'name',
}, {
  title: '分数',
  dataIndex: 'totalScore',
}
];

export default {
  name: "StuScore",
  setup(){
    //保存查询到的学生成绩信息
    const dataSource = ref([]);

    //拿到登录对象
    const loginStu = computed(()=>{
      return store.state.stu;
    })

    const stuGrade = ()=>{
      axios.get("api/queryScore/"+loginStu.value.id).then(res=>{
        dataSource.value = res.data.data;
        console.log("分数",dataSource.value);
      })
    }

    onMounted(()=>{
      stuGrade();
    })

    return{
      columns,
      dataSource
    }
  }
}
</script>

<style scoped>

</style>