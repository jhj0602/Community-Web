<template>
  <v-container class="privacy-wrap" style="background-color: lightslategray">
    <span style="font-size: 50px" class="mx-10">My Page</span>
    <v-container class="information-wrap">
      <v-avatar size="150" v-if="this.$store.state.users.profileImage == null">
        <v-img src="@/assets/default_profile.png" />
      </v-avatar>
      <v-avatar size="150" v-else>
        <v-img
          v-bind:src="
            this.$store.state.users.profileImage | loadImgOrPlaceholder
          "
        ></v-img>
      </v-avatar>
      <div class="privacy">
        <span class="user-name" style="font-max-size: 40px">{{ this.$store.state.users.nickname }}</span>
        <br />
        <span>수정일 : {{ this.$store.state.users.modifiedDate }}</span>
        <br />
        <router-link to="/editUser" style="color: white" v-if="isLogin">
          <button>회원정보 변경</button>
        </router-link>
        <span class="hidden-xs-only">&nbsp;/&nbsp;</span>
        <button @click="logout">로그아웃</button>
        <br />
      </div>
    </v-container>
  </v-container>
</template>
<script>
import { mapActions } from "vuex";
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  data() {
    return {};
  },
  created() {
    this.getUserDetails();
  },
  mounted() {},
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
