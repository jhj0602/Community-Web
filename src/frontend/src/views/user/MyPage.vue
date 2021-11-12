<template>
  <v-main class="main">
    <v-container class="privacy-wrap" style="background-color: lightslategray">
      <span style="font-size: 50px" class="mx-10">My Page</span>
      <v-container class="information-wrap">
        <v-avatar
          size="150px"
          v-if="
            this.$store.state.users.profileImage == null ||
            !this.$store.state.users.profileImage
          "
        >
          <v-img src="@/assets/default_profile.png"></v-img>
        </v-avatar>
        <v-avatar size="40px" v-else>
          <v-img
            v-bind:src="
              this.$store.state.users.profileImage | loadImgOrPlaceholder
            "
            alt="@/assets/default_profile.png"
          ></v-img>
        </v-avatar>
        <div class="privacy">
          <span class="user-name" style="font-max-size: 40px">{{
            this.$store.state.users.nickname
          }}</span>
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
    <v-card width="400" class="mx-auto">
      <v-card-text>
        <div class="font-weight-bold ml-8 mb-2">My Story</div>

        <v-timeline align-top dense>
          <v-timeline-item
            v-for="(message,i) in userPostList"
            :key="`message-${i}`"
            small
          >
            <div>
              <div class="font-weight-normal">
                <strong>{{ message.title }}</strong>
              </div>
              <div>작성일 @{{ message.modifiedDate }}</div>
              <div>{{ message.content }}</div>
            </div>
            <br />
            <div>
              <v-row align="center" justify="space-around">
                <v-btn
                  icon
                  color="blue"
                  :to="{ name: 'PostUpdate' ,params:{id:message.id} }"
                >
                  <v-icon left> mdi-pencil </v-icon>
                  수정
                </v-btn>
                <v-btn icon color="red">
                  <v-icon left> mdi-delete </v-icon>
                  삭제
                </v-btn>
              </v-row>
            </div>
          </v-timeline-item>
        </v-timeline>
      </v-card-text>
    </v-card>
    <br />
  </v-main>
</template>
<script>
import { mapActions } from "vuex";
import myMixin from "@/filter";
import axios from "axios";

export default {
  mixins: [myMixin],
  data() {
    return {
      userPostList: [],
    };
  },
  created() {
    this.getUserDetails();
    this.getUserPostListData();
  },
  mounted() {},
  methods: {
    ...mapActions({ logout: "users/logout" }),
    ...mapActions({ getMyDetail: "users/details" }),

    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({ name: "Home" });
      }
    },
    async getUserDetails() {
      if (!(await this.getMyDetail(this.$store.state.users.id))) {
        await this.$router.push({ name: "Home" });
      }
    },
    getUserPostListData() {
      axios
        .get("/api/posts/detail/user/" + this.$store.state.users.id)
        .then((res) => {
          this.userPostList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
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
