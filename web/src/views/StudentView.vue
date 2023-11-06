<template>
<!--  条件查询-->
  <a-form
      layout="inline"
      :model="queryForm"
  >
    <a-form-item>
      <a-input v-model:value="queryForm.username" placeholder="学生名">
      </a-input>
    </a-form-item>

    <a-form-item>
      <a-button
          type="primary"
          html-type="submit"
          @click="query"
      >
        查询
      </a-button>
    </a-form-item>

<!--    添加-->
    <a-form-item>
      <a-button type="primary" @click="addStu">添加</a-button>
    </a-form-item>
  </a-form>

<!--  添加或修改学生时弹出的对话框-->
  <a-modal v-model:visible="visible" title="添加或修改学生" @ok="handleOk">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="学生姓名">
        <a-input v-model:value="formState.username" />
      </a-form-item>

      <a-form-item label="账号密码">
        <a-input v-model:value="formState.password" />
      </a-form-item>

      <a-form-item label="电话号码">
        <a-input v-model:value="formState.tel" />
      </a-form-item>

      <a-form-item label="电子邮箱">
        <a-input v-model:value="formState.email" />
      </a-form-item>

      <a-form-item label="学号">
        <a-input v-model:value="formState.stuNum" />
      </a-form-item>

      <a-form-item label="昵称">
        <a-input v-model:value="formState.nickName" />
      </a-form-item>
    </a-form>
  </a-modal>

<!--  学生列表显示-->
  <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" @change="pageChange">
    <template #bodyCell="{ column,record }">

      <template v-if="column.dataIndex === 'operation'">
        <a @click="edit(record)">修改</a> |
        <a-popconfirm
            v-if="dataSource.length"
            title="Sure to delete?"
            @confirm="onDelete(record.id)"
        >
          <a>删除</a>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
</template>
<script>
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
//表头字段
const columns = [{
  title: 'id',
  dataIndex: 'id',
}, {
  title: '用户名',
  dataIndex: 'username',
}, {
  title: '密码',
  dataIndex: 'password',
}, {
  title: '电话号码',
  dataIndex: 'tel',
}, {
  title: '电子邮箱',
  dataIndex: 'email',
}, {
  title: '学号',
  dataIndex: 'stuNum',
}, {
  title: '昵称',
  dataIndex: 'nickName',
},{
  title: '操作',
  dataIndex: 'operation'
}
];

const pagination = ref({
  current: 1,
  pageSize: 2,
  total: 0
})
export default defineComponent({
  data() {
    //从后端查询到的数据
    const dataSource = ref([]);

    //控制对话框的开闭
    const visible = ref(false);

    //保存添加或修改的数据
    const formState = ref({});

    //保存输入的查询信息
    const queryForm = ref({});

    //点击对话框中的确认按钮时触发此事件
    const handleOk = () => {
      //添加或修改数据
      axios.post("api/saveStu",formState.value).then(res=>{
        if (res.data.code === "200" ){
          message.success(res.data.msg);
          load();

          //关闭对话框
          visible.value = false;
        }else {
          message.error(res.data.msg);
        }
      })
    };

    //数据查询
    const load = ()=>{
      axios.get("api/selectStu",{
        params:{
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize,
          username: queryForm.value.username
        }
      }).then(res=>{
        dataSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
      })
    }

    //分页的页数改变时触发
    const pageChange = (page)=>{
      pagination.value.current = page.current;
      load();
    }

    //查询点击事件
    const query = ()=>{
      pagination.value.current = 1;
      load();
    }

    //删除学生
    const onDelete = (id)=>{
      axios.post("api/delStu?id="+id).then(()=>{
        load();
      })
    }

    //添加学生
    const addStu = ()=>{
      //将formState中的数据清空
      formState.value = {};

      //打开对话框
      visible.value = true;
    }

    //修改学生
    const edit = (record)=>{
        //将要修改的对象显示在对话框中
        formState.value = JSON.parse(JSON.stringify(record));

        //打开对话框
        visible.value = true;
    }

    //生命周期函数 挂载时调用
    onMounted(()=>{
      load();
    })

    return {
      dataSource,
      columns,
      pagination,
      pageChange,
      onDelete,
      edit,
      addStu,
      visible,
      handleOk,
      labelCol: {
        style: {
          width: '150px',
        },
      },
      wrapperCol: {
        span: 14,
      },
      formState,
      queryForm,
      query
    };
  },
});
</script>