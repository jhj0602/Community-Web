<template>
  <div>
    <div>
      <v-card width="400" class="mx-auto mt-5">
        <div class="purple darken-2 text-center">
          <v-card-title><span class="white--text">Login</span></v-card-title>
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
              color="deep-purple accent-7"
              class="mr-2 white--text"
              @click="signIn"
          >
           Login
          </v-btn>
          <v-btn :to="{ name: 'SignUp' }" color="deep-purple accent-7" class="mr-2 white--text">
            Sign Up
          </v-btn>
          <v-btn
              color="deep-purple accent-7"
              class="mr-2 white--text"
              @click="reset"
          >
            Reset
            <v-icon right>mdi-backup-restore</v-icon>
          </v-btn>

        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>
<script>
import {mapActions} from "vuex";

export default {
  methods: {
    ...mapActions({login: "users/login"}),
    ...mapActions({detail: "users/details"}),
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    async signIn() {
      let userSignupDto = {
        email   : this.email,
        password: this.password,
      };
      if (await this.login(userSignupDto)) {
        await this.detail(this.$store.state.users.id);
        await this.$router.push({name: "Home"});
      }
    },
  },

  data: () => ({
    valid        : true,
    password     : "",
    passwordRules: [
      (v) => !!v || "password is required",
      (v) =>
          (v && v.length <= 10) || "password must be less than 10 characters",
    ],
    email        : "",
    emailRules   : [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
  }),
};
</script>
