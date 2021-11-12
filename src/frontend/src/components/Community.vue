<template>
  <v-container>
    <div v-for="(item, i) in listData" :key="`item-${i}`">
      <v-card
        max-width="400"
        class="mx-auto"
        style="background: linear-gradient(-80deg, lightblue, whitesmoke)"
      >
        <v-list-item three-line>
          <v-list-item-avatar>
            <div
              v-if="item.writerProfileImage == null || !item.writerProfileImage"
            >
              <v-avatar size="50px" color="grey">
                <v-img src="@/assets/default_profile.png"></v-img>
              </v-avatar>
            </div>
            <div v-else>
              <v-avatar size="50px">
                <v-img
                  v-bind:src="item.writerProfileImage | loadImgOrPlaceholder"
                  alt="@/assets/default_profile.png"
                ></v-img>
              </v-avatar></div
          ></v-list-item-avatar>

          <v-list-item-content class="text--white">
            <div class="text-overline mb-0">{{ item.writer }}</div>
            <div class="text-overline mb-0">
              {{ item.modifiedDate | timeForToday }}
            </div>
          </v-list-item-content>
        </v-list-item>
        <v-expansion-panels style="outline: 0">
          <v-expansion-panel>
            <v-expansion-panel-header>{{
              item.title
            }}</v-expansion-panel-header>
            <v-expansion-panel-content>
              {{ item.content }}
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
        <v-carousel
          hide-delimiter-background
          delimiter-icon="mdi-minus"
          height="300"
        >
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            height="100%"
          ></v-carousel-item>
        </v-carousel>
        <v-list one-line>
          <v-card-actions>
            <v-btn icon>
              <v-icon color="pink darken-1">mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-bookmark</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
          </v-card-actions>
        </v-list>
      </v-card>
      <br />
    </div>

    <br />
  </v-container>
</template>

<script>
import axios from "axios";
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  name: "Community",
  data() {
    return {
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
        },

        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
      cycle: false,
      show: [],
      listData: [],
      panel: [],
    };
  },
  created() {
    this.getPostListData();
  },
  methods: {
    showContent(i) {
      console.log(i + " " + this.show[i]);
      if (this.show[i]) {
        this.show[i] = false;
      } else {
        this.show[i] = true;
      }
    },
    getPostListData() {
      axios
        .get("/api/posts")
        .then((res) => {
          this.listData = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
