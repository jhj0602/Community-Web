<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link>
      |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
<script>
import axios from 'axios';


export default {
  data() {
    return {};
  },

  computed: {},
  mounted() {
    this.init();
  },
  methods : {
    init() {
      return axios
          .get("/api/hello")
          .then(res => {

          console.log(res);
          })
          .catch(err => {
            console.log(err);
          });
    },

  }
};
</script>
