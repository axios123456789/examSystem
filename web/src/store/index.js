import { createStore } from 'vuex'

export default createStore({
  //提供唯一的公共数据源，所有共享的数据统一放到其中进行存储，相似于data
  state: {
    user: SessionStorage.get("USER") || {}
  },

  //类似于vue中的computed，进行缓存，对于store中的数据进行加工处理形成新的数据
  getters: {
  },

  //定义方法，用于操作state数据
  mutations: {
    //保存user
    setUser(state, user){
      state.user = user
      SessionStorage.set("USER",user)
    }
  },

  //和mutations相似，mutations不能进行异步操作，若要进行异步操作，就得使用action
  actions: {
  },

  //模块: 当遇见大型项目时，数据量大，store就会显得臃肿。 为了解决以上问题， Vuex允许我们将store分割成模块（module）
  modules: {
  }
})
