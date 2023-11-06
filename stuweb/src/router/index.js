import { createRouter, createWebHashHistory } from 'vue-router'
import PaperListView from "@/views/PaperListView";
import PaperContent from "@/views/PaperContent";
import StuScore from "@/views/StuScore";
import LoginOut from "@/views/LoginOut";

const routes = [
  {
    path: "/paperList",
    component: PaperListView
  },
  {
    path: "/paperContent",
    component: PaperContent
  },
  {
    path: "/stuScore",
    component: StuScore
  },
  {
    path: "/loginOut",
    component: LoginOut
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
