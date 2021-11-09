<template>
  <v-container>
    <v-card
        max-width="400"
        class="mx-auto"
    >
      <v-list-item two-line>
        <v-list-item-avatar
            size="50"
            color="grey"
        ></v-list-item-avatar>
        <v-list-item-content>
          <div class="text-overline mb-0">
            OVERLINE
          </div>
        </v-list-item-content>
      </v-list-item>
      <v-carousel
          :continuous="false"
          :cycle="cycle"
          :show-arrows="false"
          hide-delimiter-background
          delimiter-icon="mdi-minus"
          height="300"
      >
        <v-carousel-item
            v-for="(slide, i) in slides"
            :key="i"
        >
          <v-sheet
              :color="colors[i]"
              height="100%"
              tile
          >
            <v-row
                class="fill-height"
                align="center"
                justify="center"
            >
              <div class="text-h2">
                {{ slide }} Slide
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
      <v-list two-line>
        <v-card-actions>
          <v-btn icon>
            <v-icon color="pink darken-1" >mdi-heart</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>mdi-bookmark</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>mdi-share-variant</v-icon>
          </v-btn>
          <v-btn
              right
              icon
              @click="show = !show"
          >
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
        </v-card-actions>

        <v-expand-transition>
          <div v-show="show">
            <v-divider></v-divider>

            <v-card-text>
              I'm a thing. But, like most politicians, he promised more than he could deliver. You won't have time for sleeping, soldier, not with all the bed making you'll be doing. Then we'll go with that data file! Hey, you add a one and two zeros to that or we walk! You're going to do his laundry? I've got to find a way to escape.
            </v-card-text>
          </div>
        </v-expand-transition>


      </v-list>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "Community",
  data () {
    return {
      show: false,
      colors: [
        'green',
        'secondary',
        'yellow darken-4',
        'red lighten-2',
        'orange darken-1',
      ],
      cycle: false,
      slides: [
        'First',
        'Second',
        'Third',
        'Fourth',
        'Fifth',
      ],
    }
  },
};
</script>
