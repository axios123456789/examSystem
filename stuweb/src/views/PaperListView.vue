<template>
    <a-table :dataSource="dataSource" :columns="columns" :pagination="pagination" @change="pageChange">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-popconfirm
              v-if="dataSource.length"
              title="是否开始考试？"
              @confirm="onBegin(record)"
          >
            <a-button type="primary">开始考试</a-button>
          </a-popconfirm>
        </template>

<!--        渲染试卷难度-->
        <template v-if="column.dataIndex === 'levelid'">
          {{getLevelName(record.levelid)}}
        </template>
      </template>
    </a-table>
</template>
<script>
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import {useRouter} from "vue-router";
import store from "@/store";

//表头
const columns = [ {
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

//分页
const pagination = ref({
  current: 1,
  pageSize: 2,
  total: 0
});

export default {
  setup() {
    //保存查到的试卷信息数据
    const dataSource = ref([]);

    //保存查到的试卷难度数据
    const levelList = ref([]);

    //路由对象
    const router = useRouter();

    //拿到登录对象
    const loginStu = computed(()=>{
      return store.state.stu;
    })

    //保存学生考试过的信息
    const stuData = ref([]);

    //查询试卷信息
    const load = ()=>{
      axios.get("api/selectPaper",{
        params: {
          pageNum: pagination.value.current,
          pageSize: pagination.value.pageSize,
        }
      }).then(res =>{
        dataSource.value = res.data.data.list;
        pagination.value.total = res.data.data.total;
      })
    }

    //开始考试
    const onBegin = (record)=>{
      //判断是否在考试时间内
      let currentTime = new Date(); //当前时间

      //查询学生考试过的试卷信息
      axios.get("api/selectPaperTestRecode",{
        params:{
          stuId: loginStu.value.id,
          paperId: record.id
        }
      }).then(res=>{
        stuData.value = res.data.data;
      })

      if (currentTime < new Date(record.startTime)){
        message.error("考试时间还没开始");
        return;
      }
      if (currentTime > new Date(record.endTime)) {
        message.error("考试时间已经结束");
        return;
      }
      setTimeout(()=>{
        //判断学生是否考过该试卷
        if (stuData !== null && stuData.value.length !== 0){
          message.warning("该试卷已经考过");
          console.log("试卷信息",stuData);
          return;
        }

        //路由跳转 考试页面
        router.push({path:'/paperContent', query:{id:record.id}})
      },100)

    }

    //查询试卷难度
    const levelLoad = ()=>{
      axios.get("api/selectLevel").then(res=>{
        levelList.value = res.data.data;
      })
    }

    //根据levelid获取试卷难度名称
    const getLevelName = (levelid)=>{
      for (let i = 0; i < levelList.value.length; i++) {
        if (levelid === levelList.value[i].id) {
          return levelList.value[i].name;
        }
      }
    }

    //点击换页即页数改变时触发
    const pageChange = (page)=>{
      pagination.value.current = page.current;
      load();
    }

    //生命周期函数 挂载时调用
    onMounted(()=>{
      load();
      levelLoad();
    })

    return {
      columns,
      dataSource,
      pagination,
      pageChange,
      onBegin,
      getLevelName
    };
  },
};
</script>