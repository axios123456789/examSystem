<template>
  <!--  条件查询-->
  <a-form
      layout="inline"
      :model="queryForm"
  >
    <a-form-item>
      <a-input v-model:value="queryForm.questionTitle" placeholder="题目名">
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
      <a-button type="primary" @click="addQuestion">添加</a-button>
    </a-form-item>
  </a-form>

  <!--  添加或修改题目时弹出的对话框-->
  <a-modal v-model:visible="visible" title="添加或修改题库" @ok="handleOk">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="题目">
        <a-input v-model:value="formState.questionTitle" type="textarea" />
      </a-form-item>

      <a-form-item label="题目类型">
        <a-select
            ref="select"
            v-model:value="formState.q_typeid"
            :options="typeList"
            :field-names="{ label: 'name', value: 'id', options: 'children' }"
        ></a-select>
      </a-form-item>

      <a-form-item label="答案">
<!--        如果是选择题-->
        <template v-if="formState.q_typeid === 1">
          <a-input v-model:value="questionxztOptions.optionA" placeholder="选项A"></a-input>
          <a-input v-model:value="questionxztOptions.optionB" placeholder="选项B"></a-input>
          <a-input v-model:value="questionxztOptions.optionC" placeholder="选项C"></a-input>
          <a-input v-model:value="questionxztOptions.optionD" placeholder="选项D"></a-input>
          <a-input v-model:value="formState.questionAnswer" placeholder="答案"></a-input>
        </template>

<!--        填空题-->
        <a-input v-if="formState.q_typeid === 2" v-model:value="formState.questionAnswer" />

<!--        判断题-->
        <template v-if="formState.q_typeid === 3">
          <a-radio-group v-model:value="formState.questionAnswer">
            <a-radio value="0">正确</a-radio>
            <a-radio value="1">错误</a-radio>
          </a-radio-group>
        </template>

<!--        简答题-->
        <template v-if="formState.q_typeid === 4">
          老师阅卷
        </template>
      </a-form-item>

      <a-form-item label="题目分数">
        <a-input v-model:value="formState.grade" />
      </a-form-item>
    </a-form>
  </a-modal>

  <!--  题库列表显示-->
  <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" @change="pageChange">
    <template #bodyCell="{ column,record }">
<!--      渲染题目类型-->
      <template v-if="column.dataIndex === 'q_typeid'">
        {{ getQuestionType(record.q_typeid) }}
      </template>

<!--      如果是判断题目 就显 对或错-->
      <template v-else-if="column.dataIndex === 'questionAnswer'">
          {{ record.q_typeid === 3 ? (record.questionAnswer === '0' ? '✓':'✗') : record.questionAnswer }}
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
//表头字段
const columns = [{
  title: 'id',
  dataIndex: 'id',
}, {
  title: '题目',
  dataIndex: 'questionTitle',
  width: 520
},
  {
    title: '题目类型',
    dataIndex: 'q_typeid'
  },
  {
  title: '答案',
  dataIndex: 'questionAnswer',
}, {
  title: '分数',
  dataIndex: 'grade',
}, {
  title: '创建时间',
  dataIndex: 'createTime',
},{
  title: '操作',
  dataIndex: 'operation'
}
];

const pagination = ref({
  current: 1,
  pageSize: 5,
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

    //保存题目类型数据
    const typeList = ref([]);

    //选择题目 选项的对象
    const questionxztOptions = ref({});

    //点击对话框中的确认按钮时触发此事件
    const handleOk = () => {
      //将选择题的选项对象保存到formState中去
      formState.value.questionxztOptions = questionxztOptions.value;

      //添加或修改数据
      axios.post("api/saveQuestion",formState.value).then(res=>{
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
      axios.get("api/selectQuestion",{
        params:{
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize,
          questionTitle: queryForm.value.questionTitle
        }
      }).then(res=>{
        dataSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
      })
    }

    //查询题目类型
    const type = ()=>{
      axios.get("api/selectType").then(res =>{
        typeList.value = res.data.data;
      })
    }

    //分页的页数改变时触发
    const pageChange = (page)=>{
      pagination.value.current = page.current;
      load();
    }

    //渲染题目类型
    const getQuestionType = (q_typeid)=>{
      for (let i=0; i<typeList.value.length; i++){
        if (q_typeid === typeList.value[i].id){
          return typeList.value[i].name;
        }
      }
    }

    //查询点击事件
    const query = ()=>{
      pagination.value.current = 1;
      load();
    }

    //删除题目
    const onDelete = (id)=>{
      axios.post("api/delQuestion?id="+id).then(()=>{
        load();
      })
    }

    //添加题目
    const addQuestion = ()=>{
      //将选项清空
      questionxztOptions.value = {};

      //将formState中的数据清空
      formState.value = {};

      //打开对话框
      visible.value = true;
    }

    //修改题目
    const edit = (record)=>{
      //将要修改的对象显示在对话框中
      formState.value = JSON.parse(JSON.stringify(record));

      //判断题目如果是选择题 要查询选择题选项 进行赋值 questionxztOptions
      if (formState.value.q_typeid === 1){
          //查询选择题选项
          axios.get("api/selectXztOptions/"+record.id).then(res =>{
            questionxztOptions.value = res.data.data;
          })
      }

      //打开对话框
      visible.value = true;
    }

    //生命周期函数 挂载时调用
    onMounted(()=>{
      load();
      type();
    })

    return {
      dataSource,
      columns,
      pagination,
      pageChange,
      onDelete,
      edit,
      addQuestion,
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
      query,
      typeList,
      getQuestionType,
      questionxztOptions
    };
  },
});
</script>