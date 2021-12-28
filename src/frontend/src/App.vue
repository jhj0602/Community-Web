<template>
  <v-app>
    <div class="main">
      <v-main>
        <v-app-bar style="background: #952175" dense dark height="70">
          <v-toolbar-title>
            <v-btn icon @click="$router.go(-1)">
              <v-icon size="xx-large" color="white">mdi-arrow-left</v-icon>
            </v-btn>
          </v-toolbar-title>
          <v-list-item>
            <v-badge avatar overlap bottom icon="mdi-plus">
              <v-avatar
                  size="40px"
                  v-if="
                  this.$store.state.users.profileImage == null ||
                  !this.$store.state.users.profileImage
                "
                  @click="toMyPage"
              >
                <v-img src="@/assets/default_profile.png"></v-img>
              </v-avatar>
              <v-avatar size="40px" v-else
                        @click="toMyPage">
                <v-img
                    v-bind:src="
                    this.$store.state.users.profileImage | loadImgOrPlaceholder
                  "
                    alt="@/assets/default_profile.png"
                ></v-img>
              </v-avatar>
            </v-badge>

            <v-toolbar-title class="ma-3" v-if="!isLogin"
            >SpringBoot, Vue로 구현하는 인별그램
            </v-toolbar-title>
            <v-toolbar-title class="ma-3" v-if="isLogin"
            >{{ this.$store.state.users.nickname }}
            </v-toolbar-title>
            <v-spacer></v-spacer>
          </v-list-item>
          <v-btn icon>
            <v-icon>mdi-heart</v-icon>
          </v-btn>
          <v-menu left bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon v-bind="attrs" v-on="on">
                <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </template>

            <v-list>
              <v-list-item v-if="!isLogin" :to="{ name: 'Login' }">
                <v-list-item-title>Login</v-list-item-title>
              </v-list-item>
              <v-list-item
                  v-if="isLogin"
                  @click="logout"
                  :to="{ name: 'Login' }"
              >
                <v-list-item-title>Logout</v-list-item-title>
              </v-list-item>
              <v-list-item v-if="isLogin" to="/user/edit">
                <v-list-item-title>회원정보 수정</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-app-bar>
      </v-main>
    </div>

    <v-main class="main">
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
    <v-bottom-navigation fixed color="primary">
      <v-btn :to="{ name: 'Home' }">
        <v-icon color="blue" large>mdi-home</v-icon>
      </v-btn>
      <v-btn>
        <v-icon color="blue" large>mdi-magnify</v-icon>
      </v-btn>
      <v-btn :to="{ name: 'PostCreate' }">
        <v-icon color="blue" large>mdi-plus</v-icon>
      </v-btn>
      <v-btn :to="{ name: 'MyPage' }">
        <v-icon color="blue" large>mdi-account</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script>
import {mapActions} from "vuex";
import myMixin from "@/filter";

export default {
  name      : "App",
  components: {},
  mixins    : [myMixin],
  mounted() {
  },

  data    : () => ({
    searchModel: null,
    drawer     : false,
    group      : null,
    is         : false,
    nickname   : "SSAFY WEB",
    items      : [
      {title: "Home", icon: "mdi-view-dashboard"},
      {title: "Mypage", icon: "mdi-forum"},
    ],
  }),
  methods : {
    ...mapActions({logout: "users/logout"}),
    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({name: "Home"});
      }
    },

    toMyPage() {
      this.$router.push({name: "MyPage"});
    },
  },
  computed: {
    isLogin() {
      return (
          this.$store.state.users.jwt != undefined ||
          this.$store.state.users.jwt == ""
      );
    },
  },
};
</script>
<style scoped>
.main {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
}
</style>
