import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import store from "../store/modules/users";

Vue.use(VueRouter);
const Login = () => import("../views/user/Login.vue");
const SignUp = () => import("../views/user/SignUp.vue");
const EditUser = () => import("../views/user/EditUser.vue");
const MyPage = () => import("../views/user/MyPage.vue");
const FindByUser = () => import("../views/user/FindByUser.vue");
const PostCreate = () => import("../views/post/PostCreate");
const PostDetail = () => import("../views/post/PostDetail");
const PostUpdate = () => import("../views/post/PostUpdate");

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/singup",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/user/edit",
    name: "EditUser",
    component: EditUser,
    meta: { authRequired: true },
  },
  {
    path: "/user/find",
    name: "FindByUser",
    component: FindByUser,
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPage,
    meta: { authRequired: true },
  },
  {
    path: "/post/create",
    name: "PostCreate",
    component: PostCreate,
    meta: { authRequired: true },
  },
  {
    path: "/post/detail/:id",
    name: "PostDetail",
    component: PostDetail,
    props: true,
  },
  {
    path: "/post/update/:id",
    name: "PostUpdate",
    component: PostUpdate,
    props: true,
    meta: { authRequired: true },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach(function (to, from, next) {
  if (to.matched.some((routeInfo) => routeInfo.meta.authRequired)) {
    if (store.state.jwt === null) {
      if (confirm("로그인이 필요합니다.\n로그인 페이지로 이동하시겠습니까?")) {
        router.push("Login");
      }
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
