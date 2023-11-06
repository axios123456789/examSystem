<template>
  <a-row>
    <a-col :span="5">

      <a-tree
          @select="onSelect"
          :tree-data="paperList"
          :field-names="{title: 'name', key: 'id'}">

      </a-tree>
    </a-col>
    <a-col :span="19">
      <a-typography-title :level="2">题库</a-typography-title>
      <a-button type="primary" @click="zujuan">组卷</a-button>&nbsp;&nbsp;

<!-- 题目预览     -->
      <a-button type="primary" @click="showDrawer">题目预览</a-button>
      <a-drawer
          v-model:visible="visible"
          class="custom-class"
          title="试卷的题目"
          placement="right"
          width="800"
      >
        <a-typography-title style="text-align: center; color: red"> {{preView.name}} </a-typography-title>
        <template v-for="(item,index) in preView.questionList" :key="index">
          <a-typography-title :level="5">
            {{index+1}}. {{item.questionTitle}} ({{item.grade}}分)
          </a-typography-title>

<!--          选择题目-->
          <template v-if="item.q_typeid == 1">
            <a-radio-group>
              <a-radio value="A">A {{item.questionxztOptions.optionA}}</a-radio><br>
              <a-radio value="B">B {{item.questionxztOptions.optionB}}</a-radio><br>
              <a-radio value="C">C {{item.questionxztOptions.optionC}}</a-radio><br>
              <a-radio value="D">D {{item.questionxztOptions.optionD}}</a-radio><br>
            </a-radio-group>
          </template>

<!--        填空题-->
          <template v-if="item.q_typeid == 2">
            <a-input />
          </template>

<!--          判断题-->
          <template v-if="item.q_typeid == 3">
            <a-radio-group>
              <a-radio value="0">正确</a-radio>
              <a-radio value="1">错误</a-radio>
            </a-radio-group>
          </template>

<!--          简答题-->
          <template v-if="item.q_typeid == 4">
            <a-textarea placeholder="Basic usage" :rows="4" />
          </template>

          <br>
          <br>
        </template>
      </a-drawer>

      <!--  题库列表显示-->
      <a-table :scroll="{y:400}" :row-key="record=>record.id" :row-selection="rowSelection" :columns="columns" :data-source="questionSource" :pagination="pagination" @change="pageChange">
        <template #bodyCell="{ column,record }">
          <!--      渲染题目类型-->
          <template v-if="column.dataIndex === 'q_typeid'">
            {{ getQuestionType(record.q_typeid) }}
          </template>

          <!--      如果是判断题目 就显 对或错-->
          <template v-else-if="column.dataIndex === 'questionAnswer'">
            {{ record.q_typeid === 3 ? (record.questionAnswer === '0' ? '✓':'✗') : record.questionAnswer }}
          </template>

        </template>
      </a-table>
    </a-col>
  </a-row>
</template>

<script>
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";

const columns = [
  /*{
    title: 'id',
    dataIndex: 'id'
  },*/
  {
    title: '题目',
    dataIndex: 'questionTitle',
    width: 320
  },
  {
    title: '题目类型',
    dataIndex: 'q_typeid',
    width: 90
  },
  {
    title: '答案',
    dataIndex: 'questionAnswer',
    width: 120
  },
  {
    title: '分数',
    dataIndex: 'grade',
    width: 60
  },
  {
    title: '创建时间',
    dataIndex: 'createTime'
  }
];

const pagination = ref({
  current: 1,
  pageSize: 5000,
  total: 0
});

export default {
  name: "ZujunaView",
  setup(){
    //保存查到的试卷
    const paperList = ref([])

    //保存查到的题目
    const questionSource = ref([]);

    //保存查询到的题目类型
    const questionType = ref([]);

    //保存查询到的需要预览的数据
    const preView = ref([]);

    //点击试卷时获取id
    let paperid = 0;

    //获取选中的题目id
    const questionIds = ref([]);

    //存放从后端查询到的试卷问题
    let paperQuestion = [];

    //先将抽屉设为关闭状态
    const visible = ref(false);

    //查询试卷
    const paperLoad = ()=>{
      axios.get("api/selectPaper",{
        params:{
          pageNum: 1,
          pageSize: 1000
        }
      }).then(res =>{
          paperList.value = res.data.data.list;
      })
    }

    //查询题库
    const questionLoad = ()=>{
      axios.get("api/selectQuestion",{
        params:{
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize
        }
      }).then(res =>{
        questionSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
      })
    }

    //查询题目类型
    const typeLoad = ()=>{
      axios.get("api/selectType").then(res =>{
        questionType.value = res.data.data;
      })
    }

    //分页的页数改变时触发
    const pageChange = (page)=>{
      pagination.value.current = page.current;
      questionLoad();
    }

    //渲染题目类型
    const getQuestionType = (q_typeid)=>{
      for (let i=0; i<questionType.value.length;i++){
        if (q_typeid === questionType.value[i].id){
          return questionType.value[i].name;
        }
      }
    }

    //选择选中的题目
    const onChange =  (selectedRowKeys) => {
      // console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      questionIds.value = selectedRowKeys;
    }

    //回显
    const rowSelection = computed(() =>{
      return{
        selectedRowKeys: questionIds.value,
        onChange
      }
    })

    /*const rowSelection = {
     // selectedRowKeys: questionIds.value,
      onChange: (selectedRowKeys) => {
       // console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        questionIds.value = selectedRowKeys;
      },
      getCheckboxProps: record => ({
        disabled: record.name === 'Disabled User',
        // Column configuration not to be checked
        name: record.name,
      }),
    };*/

    //点击试卷列表中的试卷后触发
    const onSelect = (selectedKeys)=> {
      questionIds.value = [];

      paperid = selectedKeys[0];

      //查询试卷有什么题目
      if (paperid != undefined) {
        axios.get("api/selectPaperQuestion/" + paperid).then(res => {
          if (res.data.code == 200) {
            paperQuestion = res.data.data;
            for (let i = 0; i < paperQuestion.length; i++) {
              questionIds.value.push(paperQuestion[i]);
            }
            //console.log("题目有",questionIds.value);
          } else {
            message.error(res.data.msg)
          }
        })
      }
    }

    //组卷
    const zujuan = ()=>{
      //console.log("id",paperid);
      //console.log("题目id",questionIds);

      if (paperid == 0){
        message.warning("请点击试卷");
      }else {
        //发送请求 添加到数据库
        axios.post("api/zujuan",{
          paperId: paperid,
          questionId: questionIds.value
        }).then(res =>{
          if (res.data.code === "200"){
            message.success(res.data.msg);
          }else {
            message.error("失败");
          }
        })
      }
      //console.log("题目有",questionIds.value);
    }

    //点击预览试卷后触发
    const showDrawer = ()=>{
      if (paperid == 0){
        message.warning("请点击试卷");
      }else {
        //查询需要预览的题目
        axios.get("api/paperView/"+paperid).then(res =>{
          if (res.data.code == 200) {
             preView.value = res.data.data;

             if (preView.value == null){
               message.warning("没有题目");
             }else {
              //打开抽屉
              visible.value = true;
             }

          }else {
            message.warning("系统繁忙，请稍后");
          }
        })
      }
    }

    //挂载时调用查询试卷的方法
    onMounted(()=>{
      paperLoad();
      questionLoad();
      typeLoad();
    })

    return{
      paperList,
      columns,
      pagination,
      questionSource,
      pageChange,
      getQuestionType,
      rowSelection,
      onSelect,
      zujuan,
      visible,
      showDrawer,
      preView
    }
  }
}
</script>

<style scoped>

</style>