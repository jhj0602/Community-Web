<template>
  <div id="signUp">
    <v-card width="400" class="mx-auto mt-5">
      <v-card-title class="purple darken-2 text-center">
        <h3 style="color: white">Edit User</h3>
      </v-card-title>
      <v-card-text>
        <div id="signUpForm">
          <v-form>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="email"
                    :rules="[rules.required]"
                    label="Email address"
                    prepend-icon="mdi-account-circle"
                    readonly
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required, rules.minPw]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="New Password"
                    hint="At least 8 characters"
                    prepend-icon="mdi-lock"
                    counter
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="passwordCheck"
                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required, rules.minPw, rules.pwCheck]"
                    :type="show2 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password Check"
                    hint="Enter your password once more."
                    prepend-icon="mdi-lock"
                    counter
                    @click:append="show2 = !show2"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col>
                  <v-text-field
                    v-model="nickname"
                    :rules="[rules.minName]"
                    label="Edit Nickname"
                    prepend-icon="mdi-account"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row
                ><v-col>
                  <v-card-subtitle>
                    <h4>프로필 이미지 선택</h4>
                  </v-card-subtitle>
                  <v-file-input
                    label="profile Image"
                    filled
                    prepend-icon="mdi-camera"
                    accept=".jpg, .jpeg, .png"
                    v-model="profile_pic"
                  ></v-file-input>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn
          :disabled="!valid"
          color="deep-purple accent-7"
          class="mr-0 white--text"
          @click="allSubmit"
        >
          회원수정
          <v-icon right>mdi-arrow-right-thick</v-icon>
        </v-btn>

        <v-btn color="error" class="mr-1 white--text" @click="deleteUser">
          회원탈퇴
          <v-icon right>mdi-trash-can</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import axios from "axios";

export default {
  data() {
    return {
      show1: false,
      show2: false,
      email: this.$store.state.users.details.email,
      password: "",
      passwordCheck: "",
      nickname: "",
      valid: true,
      profile_pic: null,
      profileImage: "",
      rules: {
        required: (input) => !!input || "Required.",
        minPw: (password) => password.length >= 8 || "Min 8 characters",
        minName: (nickname) => nickname.length >= 2 || "Min 2 characters",
        pwCheck: (passwordCheck) =>
          this.password === passwordCheck || "Password mismatch",
      },
    };
  },
  created() {
    this.getUserDetails();
  },
  methods: {
    ...mapActions({ updateUser: "users/update" }),
    ...mapActions({ delUser: "users/deleteUser" }),
    ...mapActions({ logout: "users/logout" }),
    ...mapActions({ getUser: "users/details" }),

    allSubmit() {
      if (
        this.profile_pic === null ||
        this.profile_pic === undefined ||
        this.profile_pic.length === 0
      ) {
        this.profileImage = "";
        this.userObjectSend();
      } else {
        this.userImageSave();
      }
    },
    userImageSave() {
      let frmUploadImage = new FormData();
      frmUploadImage.append("images", this.profile_pic);
      axios
        .post("/api/users/create/images", frmUploadImage)
        .then((res) => {
          this.profileImage = res.data;
          this.userObjectSend();
        })
        .catch((err) => {
          alert(err.msg);
        });
    },
    async userObjectSend () {
      const userUpdateDto = {
        id: this.$store.state.users.details.id,
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname,
        profileImage: this.profileImage,
      };
      if (await this.updateUser(userUpdateDto)) {
        await this.$router.push({ name: "Login" });
      }
    },
    deleteUser: async function () {
      if (await this.delUser()) {
        await this.$router.push({ name: "Main" });
      }
    },
    logoutUser: function () {
      this.logout();
    },
    getUserDetails: function () {
      this.getUser(this.$store.state.users.id);
    },
  },
};
</script>
<style scoped></style>
