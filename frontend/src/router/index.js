
import { createRouter,createWebHistory } from 'vue-router'
import UserForm from '../components/UserForm.vue'
import UserList from '../components/UserList.vue'
export default createRouter({
 history:createWebHistory(),
 routes:[
  {path:'/',component:UserForm},
  {path:'/users',component:UserList}
 ]
})
