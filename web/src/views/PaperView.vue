<template>
  <!--  条件查询-->
  <a-form
      layout="inline"
      :model="queryForm"
  >
    <a-form-item>
      <a-input v-model:value="queryForm.name" placeholder="试卷名">
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
      <a-button type="primary" @click="addPaper">添加</a-button>
    </a-form-item>
  </a-form>

  <!--  添加或修改试卷时弹出的对话框-->
  <a-modal v-model:visible="visible" title="添加或修改试卷" @ok="handleOk">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="试卷名">
        <a-input v-model:value="formState.name" />
      </a-form-item>

      <a-form-item label="考试时间">
        <a-range-picker v-model:value="paperDate" show-time />
      </a-form-item>

      <a-form-item label="试卷难度">
        <a-select
            ref="select"
            v-model:value="formState.levelid"
            :options="levelList"
            :field-names="{ label: 'name', value: 'id', options: 'children' }"
        ></a-select>
      </a-form-item>
    </a-form>
  </a-modal>

  <!--  试卷列表显示-->
  <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" @change="pageChange">
    <template #bodyCell="{ column,record }">
<!--      渲染试卷难度-->
      <template v-if="column.dataIndex === 'levelid'">
        {{ getLevelName(record.levelid) }}
      </template>

      <template v-else-if="column.dataIndex === 'operation'">
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
import dayjs from 'dayjs';
//表头字段
const columns = [{
  title: 'id',
  dataIndex: 'id',
}, {
  title: '试卷名',
  dataIndex: 'name',
}, {
  title: '开始时间',
  dataIndex: 'startTime',
}, {
  title: '结束时间',
  dataIndex: 'endTime',
},
  {
    title: '创建时间',
    dataIndex: 'createTime'
  },
  {
  title: '试卷难度',
  dataIndex: 'levelid',
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

    //保存查询到的试卷难度
    const levelList = ref([]);

    //日期数据
    const paperDate = ref([]);

    //点击对话框中的确认按钮时触发此事件
    const handleOk = () => {
      //给开始时间和结束时间赋值
      formState.value.startTime = paperDate.value[0].format("YYYY-MM-DD HH:mm:ss");
      formState.value.endTime = paperDate.value[1].format("YYYY-MM-DD HH:mm:ss");

      //添加或修改数据
      axios.post("api/savePaper",formState.value).then(res=>{
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

    //试卷数据查询
    const load = ()=>{
      axios.get("api/selectPaper",{
        params:{
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize,
          name: queryForm.value.name
        }
      }).then(res=>{
        dataSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
      })
    }

    //试卷难度查询
    const level = ()=>{
      axios.get("api/selectLevel").then(res=>{
        //console.log("试卷难度",res.data);
        levelList.value = res.data.data;
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

    //根据levelid获取试卷难度的名称
    const getLevelName = (levelid)=>{
        for (let i=0; i<levelList.value.length; i++){
          if (levelid === levelList.value[i].id){
            return levelList.value[i].name;
          }
        }
    }

    //删除试卷
    const onDelete = (id)=>{
      axios.post("api/delPaper?id="+id).then(()=>{
        load();
      })
    }

    //添加试卷
    const addPaper = ()=>{
      //清空日期对象
      paperDate.value = [];

      //将formState中的数据清空
      formState.value = {};

      //打开对话框
      visible.value = true;
    }

    //修改试卷
    const edit = (record)=>{
      //对考试时间对象赋值
      paperDate.value = [dayjs(record.startTime, "YYYY-MM-DD HH:mm:ss"), dayjs(record.endTime, "YYYY-MM-DD HH:mm:ss")];

      //将要修改的对象显示在对话框中
      formState.value = JSON.parse(JSON.stringify(record));

      //打开对话框
      visible.value = true;
    }

    //生命周期函数 挂载时调用
    onMounted(()=>{
      load();
      level();
    })

    return {
      dataSource,
      columns,
      pagination,
      pageChange,
      onDelete,
      edit,
      addPaper,
      visible,
      handleOk,
      labelCol: {
        style: {
          width: '150px',
        },
      },
      wrapperCol: {
        span: 16,
      },
      formState,
      queryForm,
      query,
      getLevelName,
      levelList,
      paperDate
    };
  },
});
</script>