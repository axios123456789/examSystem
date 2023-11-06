<template>
  <!--  条件查询-->
  <a-form
      layout="inline"
      :model="queryForm"
  >
    <a-form-item>
      <a-input v-model:value="queryForm.username" placeholder="管理员名">
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

    <a-form-item>
      <a-button type="primary" @click="showModal">添加</a-button>
    </a-form-item>
  </a-form>

  <a-modal v-model:visible="visible" title="添加或修改管理员" @ok="handleOk">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="姓名">
        <a-input v-model:value="formState.username" />
      </a-form-item>

      <a-form-item label="密码">
        <a-input v-model:value="formState.password" />
      </a-form-item>

      <a-form-item label="电子邮箱">
        <a-input v-model:value="formState.email" />
      </a-form-item>

      <a-form-item label="电话号码">
        <a-input v-model:value="formState.tel" />
      </a-form-item>

      <a-form-item label="性别">
        <a-radio-group v-model:value="formState.sex">
          <a-radio value="男">男</a-radio>
          <a-radio value="女">女</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="头像" :key="loadKey">
        <a-upload
            name="file"
            action="api/upload"
            @change="uploadChange"
        >
          <a-button>
            <upload-outlined></upload-outlined>
            上传图片
          </a-button>
        </a-upload>
      </a-form-item>

    </a-form>
  </a-modal>

  <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" @change="pageChange">
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a @click="edit(record)">修改</a>  |
        <a-popconfirm
            v-if="dataSource.length"
            title="Sure to delete?"
            @confirm="onDelete(record.id)"
        >
          <a>删除</a>
        </a-popconfirm>
      </template>

      <!--      下载图片-->
      <template v-else-if="column.dataIndex === 'headImg'">
        <a-image
            :width="50"
            :height="50"
            :src="'api/downLoad/'+record.headImg"
            fallback="data:image/png;base64,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"
        />
      </template>
    </template>
  </a-table>
</template>
<script>
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
const columns = [
  {
    title: 'id',
    dataIndex: 'id'
  },
  {
    title: '姓名',
    dataIndex: 'username',
  }, {
    title: '密码',
    dataIndex: 'password',
  }, {
    title: '电子邮箱',
    dataIndex: 'email',
  },
  {
    title: '电话',
    dataIndex: 'tel',

  },
  {
    title: '性别',
    dataIndex: 'sex'
  },
  {
    title: '头像',
    dataIndex: 'headImg'
  },
  {
    title: '操作',
    dataIndex: 'operation',
  }];

const pagination = ref({
  current:1,
  pageSize:2,
  total:0
});
export default defineComponent({
  setup() {
    //给上传的头像的地址
    let loadKey = ref({});

    //条件查询表单
    const queryForm = ref({});

    //存放要添加或修改的数据
    const formState = ref({});

    //控制对话框的关闭 默认关闭
    const visible = ref(false);

    //点击添加后触发
    const showModal = () => {
      //添加时给头像随机赋值一个地址
      loadKey.value = Math.random();

      formState.value = {};

      //将类型设为2，即老师
      formState.value.type = 1;

      //打开对话框
      visible.value = true;
    };

    //点击对话框内确认按钮后触发的事件
    const handleOk = () => {
      //添加数据
      axios.post("api/addUser",formState.value).then(res=>{
        //console.log(res);
        if (res.data.code === "200"){
          message.success(res.data.msg);
          load();

          //关闭对话框
          visible.value = false;
        }else{
          message.error(res.data.msg)
        }

      })
    };

    const dataSource = ref([]);

    //查询
    const load = ()=>{
      axios.get("api/selectUser",{
        params:{
          type: 1,
          username: queryForm.value.username,
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize
        }
      }).then(res => {
        //console.log("数据",res);
        dataSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
        console.log("数据",dataSource.value)
      })
    }

    //分页点击 控制分页
    const pageChange = (page)=>{
      pagination.value.current = page.current;
      load();
    }

    //删除老师数据
    const onDelete = (id) =>{
      axios.post("api/delUser?id="+id).then(res=>{
        console.log(res);
        load();
      })
    }

    //修改老师数据
    const edit = (record) =>{
      //修改时给头像随机赋值一个地址
      loadKey.value = Math.random();

      //console.log("修改后",record);
      formState.value = JSON.parse(JSON.stringify(record));
      //打开对话框
      visible.value = true;
    }

    //文件上传事件 上传中 完成 失败都会调用这个函数
    const uploadChange = (fileData)=>{
      console.log("文件数据",fileData);
      if (fileData.file.response != undefined){
        formState.value.headImg = fileData.file.response.data;
      }
    }

    //条件查询点击事件
    const query = ()=>{
      //将页码改为1
      pagination.value.current = 1;

      load();
    }

    onMounted(()=>{
      load();
    })

    return {
      dataSource,
      columns,
      editingKey: '',
      /*editableData,
      edit,
      save,
      cancel,*/
      visible,
      showModal,
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
      onDelete,
      edit,
      pagination,
      pageChange,
      uploadChange,
      queryForm,
      query,
      loadKey
    };
  },
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>