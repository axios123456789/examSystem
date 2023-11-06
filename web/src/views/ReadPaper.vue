<template>
<!--  对话框 点击修改时触发-->
  <a-modal v-model:visible="visible" title="修改学生成绩" @ok="handleOk">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="分数">
        <a-input v-model:value="formState.correntScore" />
      </a-form-item>
    </a-form>
  </a-modal>

<!--  阅卷列表-->
  <a-table :dataSource="dataSource" :columns="columns">
    <template #bodyCell="{ column,record }">

      <template v-if="column.dataIndex === 'option'">
        <a @click="edit(record)">修改</a>
      </template>
    </template>
  </a-table>
</template>

<script>

import {onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";

const columns = [
  /*{
    title: 'id',
    dataIndex: 'id'
  },*/
  {
    title: '姓名',
    dataIndex: 'username'
  },
  {
    title: '试卷',
    dataIndex: 'name'
  },
  {
    title: '题目',
    dataIndex: 'questionTitle'
  },
  {
    title: '正确答案',
    dataIndex: 'questionAnswer'
  },
  {
    title:'题目分数',
    dataIndex: 'questionScore'
  },
  {
    title: '学生答案',
    dataIndex: 'correntAnswer'
  },
  {
    title: '学生分数',
    dataIndex: 'correntScore'
  },
  {
    title: '操作',
    dataIndex: 'option',
    width: 80
  }
];

export default {
  name: "ReadPaper",
  setup(){
    //保存老师阅卷时查询到的阅卷信息
    const dataSource = ref([]);

    //控制对话框关闭
    const visible = ref(false);

    //保存要修改为的分数
    const formState = ref({});

    //查询阅卷信息
    const readLoad = ()=>{
      axios.get("api/selectReadPaper").then(res =>{
        dataSource.value = res.data.data;
      })
    }

    //点击对话框中确认按钮后触发
    const handleOk = ()=>{
      console.log("correntScore",formState.value);
      axios.post("api/saveScore",{},{
        params:{
          id: formState.value.id,
          correntScore: formState.value.correntScore
        }
      }).then(res =>{
        if (res.data.code == 200){
          message.success(res.data.msg);
        }else {
          message.error(res.data.msg);
        }
      })
    }

    //修改学生成绩
    const edit = (record)=>{
      //将要修改的对象显示在对话框中
      formState.value.correntScore = JSON.parse(JSON.stringify(record.correntScore));
      formState.value.id = JSON.parse(JSON.stringify(record.id));

      //打开对话框
      visible.value = true;
    }

    //周期函数 挂载时调用
    onMounted(()=>{
      readLoad();
    })

    return{
      columns,
      dataSource,
      edit,
      labelCol: {
        style: {
          width: '150px',
        },
      },
      wrapperCol: {
        span: 14,
      },
      handleOk,
      visible,
      formState
    }
  }
}
</script>

<style scoped>

</style>