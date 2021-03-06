import axios from "axios";

const config = {
  baseUrl: "/api/users/",
};
const state = {
  jwt: null,
  id: null,
  details: null,
  profileImg:null,
  nickname: "로그인하세요.",
  modifiedDate:null,
  userList: [],
  message:""
};
const mutations = {
  setJwt: (state, jwt) => {
    state.jwt = jwt;
  },
  setId: (state, id) => {
    state.id = id;
  },
  setUserDetails: (state, data) => {
    state.details = data;
    state.nickname = data.nickname;
    state.profileImage=data.profileImage;
    state.modifiedDate=data.modifiedDate;
  },
  setUserList: (state, data) => {
    state.userList = data;
  },
  logout: (state) => {
    state.jwt = null;
    state.details = null;
    state.id = null;
    state.nickname = "로그인하세요.";
    state.profileImage=null;
    state.modifiedDate=null;
  },
};

const actions = {
  async login({ state, commit }, userSignupDto) {
    let isSuccess = true;
    await axios
      .post(`${config.baseUrl}login`, userSignupDto)
      .then((response) => {
        commit("setJwt", response.data.token);
        commit("setId", response.data.id);
        alert("로그인 되었습니다.");
      })
      .catch((error) => {
        alert("ID나 PW가 존재하지 않습니다.");
        isSuccess = false;
      });
    return isSuccess;
  },

  async logout({ commit }) {
    let isSuccess = true;

    if (state.jwt != null) {
      commit("logout");
      alert("로그아웃 되었습니다.");
    } else {
      alert("로그인 되어있지 않습니다.");
      isSuccess = false;
    }
    return isSuccess;
  },

  async signUp({ commit }, userSignupDto) {
    let isSuccess = true;
    await axios
      .post(`${config.baseUrl}`, userSignupDto)
      .then((response) => {
        alert("회원가입 성공!");
      })
      .catch((error) => {
        alert("회원가입 실패! 입력값을 확인해주세요.");
        isSuccess = false;
      });
    return isSuccess;
  },
  isDuplicatedEmail({ commit }, email) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${config.baseUrl}emails/${email}/exists`)
        .then((response) => {
          if (response.data) {
            alert("중복되지 않은 이메일입니다.");
          } else {
            alert("중복된 이메일입니다.");
          }
          resolve(response);
        })
        .catch((error) => {
          alert(error.response.data);
        });
    });
  },
  async details({ commit }, id) {
    let isSuccess = true;
    await axios
      .get(`${config.baseUrl}${id}/details`, {
        headers: { Authorization: `Bearer ${state.jwt}` },
      })
      .then((response) => {
        commit("setUserDetails", response.data);
      })
      .catch((error) => {
        alert(error.response.data);
        isSuccess = false;
      });
    return isSuccess;
  },
  async deleteUser({ commit }) {
    let isSuccess = true;
    await axios
      .delete(`${config.baseUrl}${state.details.id}/delete`, {
        headers: { Authorization: `Bearer ${state.jwt}` },
      })
      .then(() => {
        commit("logout");
        alert("계정을 삭제했습니다.");
      })
      .catch((error) => {
        alert("계정을 삭제 할 수 업습니다");
        isSuccess = false;
      });
    return isSuccess;
  },
  async deleteUserWithId({ commit }, id) {
    let isSuccess = true;
    await axios
      .delete(`${config.baseUrl}${id}/delete`, {
        headers: { Authorization: `Bearer ${state.jwt}` },
      })
      .then(() => {
      })
      .catch((error) => {
        alert(error.response.data);
        isSuccess = false;
      });
    return isSuccess;
  },

  async update({ commit }, userUpdateDto) {
    let isSuccess = true;
    await axios
      .put(`${config.baseUrl}update`, userUpdateDto, {
        headers: { Authorization: `Bearer ${state.jwt}` },
      })
      .then(() => {
        alert("계정을 수정했습니다. 다시 로그인하세요");
        commit("logout");
      })
      .catch((error) => {
        alert("계정을 수정하지 못했습니다. 입력값을 확인해주세요");
        isSuccess = false;
      });
    return isSuccess;
  },
  
  async profileImageSave({ commit },formData) {
    console.log("save")
    let isSuccess = true;
    await axios
        .post(`${config.baseUrl}/profile/image`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${state.jwt}` },
        })
        .then(() => {
          alert("프로필사진 수정완료!");
        })
        .catch((error) => {
          alert("프로필사진을 수정하지 못했습니다.");
          isSuccess = false;
        });
    return isSuccess;
  },
  async findAll({ commit }) {
    let isSuccess = true;
    await axios
      .get(`${config.baseUrl}list`, {
        headers: { Authorization: `Bearer ${state.jwt}` },
      })
      .then((response) => {
        commit("setUserList", response.data);
      })
      .catch((error) => {
        alert(error.response);
        isSuccess = false;
      });
    return isSuccess;
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
