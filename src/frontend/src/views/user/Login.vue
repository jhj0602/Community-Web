<template>
  <div>
    <div>
      <v-card width="400" class="mx-auto mt-5" >
        <div  style="background-color: #1F7085" >
          <v-card-title ><span class="white--text">Login</span></v-card-title>
        </div>
        <v-card-text>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="email"
              :rules="emailRules"
              label="E-mail"
              prepend-icon="mdi-account-circle"
              required
            ></v-text-field>
            <v-text-field
              v-model="password"
              :counter="10"
              :rules="passwordRules"
              label="password"
              prepend-icon="mdi-lock"
              required
              type="password"
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn
            :disabled="!valid"
            color="pink darken-1"
            class="white--text block"
            @click="signIn"
            rounded
          >
            Login
          </v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn
            :to="{ name: 'SignUp' }"
            color="pink darken-3"
            class="white--text block"
            rounded
          >
            Sign Up
          </v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn
            color="pink darken-4"
            class="white--text block"
            :to="{ name: 'FindByUser' }"
            rounded
          >
            ID/PW 찾기
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>
    <br />
  </div>
</template>
<script>
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions({ login: "users/login" }),
    ...mapActions({ detail: "users/details" }),
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    async signIn() {
      let userSignupDto = {
        email: this.email,
        password: this.password,
      };
      if (await this.login(userSignupDto)) {
        await this.detail(this.$store.state.users.id);
        await this.$router.push({ name: "Home" });
      }
    },
  },

  data: () => ({
    valid: true,
    password: "",
    passwordRules: [
      (v) => !!v || "password is required",
      (v) =>
        (v && v.length <= 10) || "password must be less than 10 characters",
    ],
    email: "",
    emailRules: [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
  }),
};
</script>
<style>
.block {
  display: block;
  width: 100%;
  border: none;
  text-align: center;
}
</style>
