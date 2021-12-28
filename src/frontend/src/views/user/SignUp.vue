<template>
  <div id="signUp">
    <v-card width="600" class="mx-auto mt-5">
      <div class="purple darken-1">
        <v-card-title align="center">
          <span class="white--text">Sign Up</span>
        </v-card-title>
      </div>
      <div id="signUpForm">
        <v-form>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                  solo
                  v-model="email"
                  :rules="[rules.required, rules.validateEmailType]"
                  label="Email address"
                  prepend-icon="mdi-account-circle"
                  required
                ></v-text-field>
                <v-btn
                  v-model="emailCheck"
                  id="checkEmail"
                  elevation="1"
                  color="deep-purple accent-3"
                  class="mr-3 white--text block"
                  @click="isDuplicatedEmail"
                >
                  check
                </v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                  solo
                  v-model="password"
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.required, rules.minPw]"
                  :type="show1 ? 'text' : 'password'"
                  name="input-10-1"
                  label="Password"
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
                  solo
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
                  solo
                  v-model="nickname"
                  :rules="[rules.minName]"
                  label="Nickname"
                  prepend-icon="mdi-account"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </div>

      <v-card-actions>
        <v-btn
          :disabled="
            !email ||
            !(password.length >= 8) ||
            !(passwordCheck.length >= 8) ||
            !(nickname.length >= 2)
          "
          color="deep-purple accent-7"
          class="mr-3 white--text block"
          @click="allSubmit"
        >
          Sign up
          <v-icon right>mdi-arrow-right-thick</v-icon>
        </v-btn>
      </v-card-actions>
      <v-card-actions>
        <v-btn
          color="deep-purple accent-7"
          class="mr-4 white--text block"
          @click="$router.go(-1)"
        >
          Cancel
          <v-icon right>mdi-cancel</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      show1: false,
      show2: false,
      email: "",
      emailCheck: false,
      password: "",
      passwordCheck: "",
      nickname: "",
      rules: {
        required: (input) => !!input || "Required.",
        validateEmailType: (email) =>
          /.+@.+\..+/.test(email) || "E-mail must be valid",
        minPw: (password) =>
          (password.length >= 8 && password.length <= 20) ||
          "Min 8 and Max 20 characters",
        minName: (nickName) =>
          (nickName.length >= 2 && nickName.length <= 8) ||
          "Min 2 and Max 8 characters",
        pwCheck: (passwordCheck) =>
          this.password === passwordCheck || "Password mismatch",
      },
    };
  },
  methods: {
    ...mapActions({ signup: "users/signUp" }),
    ...mapActions({ duplicateEmail: "users/isDuplicatedEmail" }),
    isDuplicatedEmail: function () {
      this.duplicateEmail(this.email);
    },
    allSubmit() {
      this.userObjectSend();
    },
    async userObjectSend() {
      const userSignupDto = {
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname,
      };
      if (await this.signup(userSignupDto)) {
        await this.$router.push({ name: "Login" });
      }
    },
  },
};
</script>
<style>
.block {
  display: block;
  width: 100%;
  border: none;
  background-color: #04aa6d;
  padding: 14px 28px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
}
</style>
