<template>
  <v-main class="main">
    <v-container class="privacy-wrap">
      <span style="font-size: 50px" class="mx-10">My Page</span>
      <v-container class="information-wrap">
        <v-avatar size="150">
          <v-img src="https://randomuser.me/api/portraits/men/78.jpg" />
        </v-avatar>
        <div class="privacy">
          <span class="user-name">{{ this.$store.state.users.nickname }}</span>
          <br />
          <router-link to="/editUser" style="color:white;" v-if="isLogin">
            <button>
              회원정보 변경
            </button>
          </router-link>
          <span class="hidden-xs-only">&nbsp;/&nbsp;</span>
          <button @click="logout">로그아웃</button>
          <br />

        </div>
      </v-container>
    </v-container>


  </v-main>
</template>
<script>
import { mapActions } from "vuex";


export default {

  data() {
    return {

    };
  },
  created() {
    this.getUserDetails();
  },
  mounted() {

  },
  methods: {
    ...mapActions({ logout: "users/logout" }),
    ...mapActions({ getMyDetail: "users/details" }),

    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({ name: "Main" });
      }
    },
    async getUserDetails() {
      if (!(await this.getMyDetail(this.$store.state.users.id))) {
        await this.$router.push({ name: "Main" });
      }
    },
    async getMyCartList() {
      await this.getMyCart(this.$store.state.users.id);
    },
  },
  computed: {
    isLogin() {
      return (
        this.$store.state.users.jwt != undefined ||
        this.$store.state.users.jwt == ""
      );
    }
  }
};
</script>
<style scoped>
.main {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
}

.privacy-wrap {
  max-width: 100%;
  background-color: black;
  color: white;
}

.information-wrap {
  display: flex;
  flex-direction: row;
  margin-top: 15px;
}

.privacy {
  margin-left: 20px;
  font-size: 20px;
}

.user-name {
  font-size: 3rem;
  letter-spacing: 7px;
}

.order-status-wrap {
  max-width: 100%;
}
</style>
