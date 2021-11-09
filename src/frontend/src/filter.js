let myMixin = {

    filters: {
        loadImgOrPlaceholder: function(path) {
            return "https://community-web-image.s3.ap-northeast-2.amazonaws.com/static/" + path;
        },
    }
};
export default myMixin;
