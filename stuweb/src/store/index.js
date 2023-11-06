import { createStore } from 'vuex'

export default createStore({
  state: {
    stu: SessionStorage.get("STU") || {}
  },
  getters: {
  },
  mutations: {
    setStu(state, stu){
      state.stu = stu
      SessionStorage.set("STU",stu)
    }
  },
  actions: {
  },
  modules: {
  }
})
