<template>
  <div>
    <v-card max-width="450" max-height="auto" class="mx-auto my-12">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-card-title>
          <v-text-field
            color="primary"
            :rules="titleRules"
            required
            v-model="title"
            counter="20"
            label="Title"
            clearable
            clear-icon="mdi-close-circle"
            outlined
          ></v-text-field>
        </v-card-title>
        <v-card-title>
          <v-textarea
            color="primary"
            :rules="contentRules"
            v-model="content"
            counter="500"
            label="Content"
            clearable
            clear-icon="mdi-close-circle"
            outlined
          ></v-textarea>
        </v-card-title>
        <v-card-subtitle>
          <h4>Image를 올려주세요</h4>
        </v-card-subtitle>
        <input
          type="file"
          ref="imageInput"
          name="images[]"
          id="photo"
          @change="imagesAdd"
          hidden
          multiple
        />
        <v-btn
          color="blue-grey"
          class="ma-2 white--text"
          @click="onClickImageUpload"
          >Imagae Upload
          <v-icon right dark> mdi-cloud-upload</v-icon>
        </v-btn>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">Image</th>
                <th class="text-left">Delete</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(img, i) in previewImage" v-bind:key="i">
                <th>
                  <v-img :src="img" width="150" height="150"></v-img>
                </th>
                <th>
                  <v-btn
                    x-small
                    dark
                    color="pink"
                    v-show="previewImage"
                    @click="removeImage(i)"
                  >
                    <v-icon dark> mdi-delete</v-icon>
                    Image {{ i + 1 }}
                  </v-btn>
                </th>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        <v-card-subtitle>
          <v-checkbox
            v-model="checkbox"
            :rules="[(v) => !!v || '게시물 등록에 동의 해주세요']"
            label="Do you agree?"
            required
          ></v-checkbox>
        </v-card-subtitle>
      </v-form>
      <v-btn
        color="blue-grey"
        block
        class="white--text"
        :disabled="!valid"
        @click="validate"
      >
        Submit
        <v-icon right color="white"> mdi-checkbox-marked-circle</v-icon>
      </v-btn>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";

export default {
  props: ["id"],
  data() {
    return {
      valid: true,
      titleRules: [
        (v) => !!v || "title is required",
        (v) => (v && v.length <= 20) || "Name must be less than 15 characters",
      ],
      contentRules: [
        (v) => !!v || "content is required",
        (v) => (v && v.length <= 500) || "50자 이하로 작성 부탁해용",
      ],
      checkbox: false,
      images: {},
      previewImage: [],
      title: "",
      content: "",
    };
  },
  created() {
    this.getPostDetail();
  },
  methods: {
    getPostDetail() {
      axios
        .get("/api/posts/detail/" + this.id)
        .then((res) => {
          this.title = res.data.title;
          this.content = res.data.content;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    validate() {
      this.$refs.form.validate();
      this.allSubmit();
    },

    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    imagesAdd(e) {
      let files = e.target.files || e.dataTransfer.files;
      this.images = [];
      Array.prototype.push.apply(this.images, files); //array element add
      if (!this.images.length) return;
      this.previewCreateImage(this.images);
    },
    previewCreateImage(file) {
      for (let i = 0; i < file.length; i++) {
        let reader = new FileReader();
        let vm = this;
        reader.onload = (e) => {
          vm.previewImage.push(e.target.result);
        };
        reader.readAsDataURL(file[i]);
      }
    },
    removeImage(key) {
      this.previewImage.splice(key, 1);
      this.images.splice(key, 1);
    },
    allSubmit() {
      let frmUploadImage = new FormData();
      for (let i = 0; i < this.images.length; i++) {
        frmUploadImage.append("images", this.images[i]);
      }

      axios
        .post("/api/posts/create/images", frmUploadImage, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          const imageObj = res.data;
          console.log(res.data);
          const productObj = {
            title: this.title,
            content: this.content,
            imageUrl: imageObj,
          };
          axios
            .post("/api/posts/update", productObj)
            .then((res) => {
              this.$router.push({
                name: "MyPage",
              });
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
