<template>
  <div>
    <a-typography-title style="text-align: center; color: red"> {{paper.name}} </a-typography-title>
    <span style="color: red;">
      {{strTime}}
    </span>
    <br><br>

    <a-form :model="scoreForm">
      <template v-for="(item,index) in paper.questionList" :key="index">
        <a-typography-title :level="5">
          {{index+1}}. {{item.questionTitle}} ({{item.grade}}分)
        </a-typography-title>

        <!--          选择题目-->
        <template v-if="item.q_typeid == 1">
          <a-radio-group v-model:value="scoreForm.correntAnswer[index]">
            <a-radio value="A">A {{item.questionxztOptions.optionA}}</a-radio><br>
            <a-radio value="B">B {{item.questionxztOptions.optionB}}</a-radio><br>
            <a-radio value="C">C {{item.questionxztOptions.optionC}}</a-radio><br>
            <a-radio value="D">D {{item.questionxztOptions.optionD}}</a-radio><br>
          </a-radio-group>
        </template>

        <!--        填空题-->
        <template v-if="item.q_typeid == 2">
          <a-input v-model:value="scoreForm.correntAnswer[index]" />
        </template>

        <!--          判断题-->
        <template v-if="item.q_typeid == 3">
          <a-radio-group v-model:value="scoreForm.correntAnswer[index]">
            <a-radio value="0">正确</a-radio>
            <a-radio value="1">错误</a-radio>
          </a-radio-group>
        </template>

        <!--          简答题-->
        <template v-if="item.q_typeid == 4">
          <a-textarea placeholder="Basic usage" :rows="4" v-model:value="scoreForm.correntAnswer[index]" />
        </template>

        <br>
        <br>
      </template>

      <a-button type="primary" @click="save">提交</a-button>
    </a-form>
  </div>
</template>

<script>
import {useRoute, useRouter} from "vue-router";
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import store from '@/store'

export default {
  name: "PaperContent",
  setup(){
    const router = useRoute()
    const userouter = useRouter();
    //console.log("id",router.query.id)

    //拿到登录对象
    const loginStu = computed(()=>{
      return store.state.stu;
    })

    //保存查询到的试卷题目
    const paper = ref([]);

    //保存要考的试卷的结束时间
    let endTime;

    //保存离考试结束还剩的时间
    const strTime = ref(`离考试结束还剩余：`);

    //获取到的表单元素 即学生答题信息
    const scoreForm = ref({
      correntAnswer:[]  //问题数组
    });

    //定义一个list集合 返回给后台 保存到数据库中
    const list = [];

    //根据试卷id查询题目
    const preView = ()=>{
      axios.get("api/paperView/"+router.query.id).then(res=>{
        if (res.data.code == 200){
          paper.value = res.data.data;

          //赋值结束时间
          endTime = new Date(res.data.data.endTime);
        }else {
          message.warning("系统繁忙，稍后再试");
        }
      })
    }

    //数据加0
    const addZero = (i)=>{
      return i < 10 ? "0" + i : i + "";
    }

    //设置定时器 1秒执行一次
    const time = setInterval(()=>{
      let currentTime = new Date();   //当前时间

      let leftTime = parseInt((endTime - currentTime) / 1000);   //剩下的时间 多少秒
      let d = parseInt(leftTime / (24*60*60));
      let h = parseInt(leftTime / (60*60) % 24);
      let m = parseInt(leftTime / 60 % 60);
      let s = parseInt(leftTime % 60);
      d = addZero(d);
      h = addZero(h);
      m = addZero(m);
      s = addZero(s);
      strTime.value = `离考试结束还剩于：${d} 天 ${h} 时 ${m} 分 ${s} 秒`;

      //考试时间结束时主动提交
      if (leftTime == 0){
        save();
      }
    },1000)

    //将一些基本的数据存入list中
    const getList = ()=>{
      for (let i = 0; i < paper.value.questionList.length; i++) {
        let item = paper.value.questionList[i];
        let score = {};
        score.stuId = loginStu.value.id;
        score.paperId = paper.value.id;
        score.questionTitle = item.questionTitle;
        score.questionId = item.id;
        score.q_typeid = item.q_typeid;
        score.questionAnswer = item.questionAnswer;
        score.questionScore = item.grade;
        list.push(score)
      }
    }

    //点击提交时触发
    const save = ()=>{
      //console.log("题目",scoreForm.value);
      //获取添加的list
      getList();

      for (let i = 0; i < list.length; i++) {
        list[i].correntAnswer = scoreForm.value.correntAnswer[i];
        if (scoreForm.value.correntAnswer[i] === list[i].questionAnswer){
          list[i].correntScore = list[i].questionScore;
        }else {
          list[i].correntScore = 0;
        }
      }

      console.log("数据",list);
      axios.post("api/savePaperTestRecord",list).then(res =>{
        if (res.data.code == 200){
          //清除定时器
          clearInterval(time);

          message.success(res.data.msg);
          userouter.push({path:'/paperList'})
        }else {
          message.error("出错了，请联系工作人员");
        }
      })
    }

    //周期函数 挂载时调用
    onMounted(()=>{
      preView();
    })

    return{
      paper,
      strTime,
      scoreForm,
      save
    }
  }
}
</script>

<style scoped>

</style>