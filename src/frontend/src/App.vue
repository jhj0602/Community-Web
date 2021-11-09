<template>
  <v-app>
    <div>
      <v-app-bar
        style="background: linear-gradient(-90deg, lightblue, purple)"
        dense
        dark
        height="70"
      >
        <v-badge avatar bordered overlap bottom icon="mdi-plus">
          <v-avatar size="40" v-if="!isLogin">
            <v-img src="@/assets/default_profile.png"></v-img>
          </v-avatar>

          <v-avatar size="40" v-if="isLogin">
            <div v-if="this.$store.state.users.profileImage == null">
              <v-img src="@/assets/default_profile.png"></v-img>
            </div>
            <div v-else>
              <v-img
                v-bind:src="
                  this.$store.state.users.profileImage | loadImgOrPlaceholder
                "
              ></v-img>
            </div>
          </v-avatar>
        </v-badge>

        <v-toolbar-title class="ma-3" v-if="!isLogin"
          >SSAFY WEB</v-toolbar-title
        >
        <v-toolbar-title class="ma-3" v-if="isLogin">{{
          this.$store.state.users.nickname
        }}</v-toolbar-title>
        <v-spacer></v-spacer>

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
            <v-list-item v-if="isLogin" @click="logout" :to="{ name: 'Login' }">
              <v-list-item-title>Logout</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="isLogin" to="/user/edit">
              <v-list-item-title>회원정보 수정</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-app-bar>
    </div>
    <v-main>
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
      <v-btn>
        <v-icon color="blue" large>mdi-plus</v-icon>
      </v-btn>
      <v-btn :to="{ name: 'Mypage' }">
        <v-icon color="blue" large>mdi-account</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </v-app>
</template>

<script>
import { mapActions } from "vuex";
import myMixin from "@/filter";
export default {
  name: "App",
  components: {},
  mixins: [myMixin],
  mounted() {},

  data: () => ({
    searchModel: null,
    drawer: false,
    group: null,
    is: false,
    nickname: "SSAFY WEB",
    baseProfileImage: "https://cdn.vuetifyjs.com/images/john.png",
    items: [
      { title: "Home", icon: "mdi-view-dashboard" },
      { title: "Mypage", icon: "mdi-forum" },
    ],
  }),
  methods: {
    ...mapActions({ logout: "users/logout" }),
    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({ name: "Main" });
      }
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
