import { createRouter, createWebHashHistory } from 'vue-router'
import StudentView from "@/views/StudentView";
import TeacherView from "@/views/TeacherView";
import UserView from "@/views/UserView";
import PaperView from "@/views/PaperView";
import LevelView from "@/views/LevelView";
import QuestionView from "@/views/QuestionView";
import ZujunaView from "@/views/ZujunaView";
import ReadPaper from "@/views/ReadPaper";

const routes = [
  {
    path: '/student',
    component: StudentView
  },
  {
    path: '/teacher',
    component: TeacherView
  },
  {
    path: '/user',
    component: UserView
  },
  {
    path: '/paper',
    component: PaperView
  },
  {
    path: '/level',
    component: LevelView
  },
  {
    path: '/question',
    component: QuestionView
  },
  {
    path: '/zujuan',
    component: ZujunaView
  },
  {
    path: '/readPaper',
    component: ReadPaper
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
