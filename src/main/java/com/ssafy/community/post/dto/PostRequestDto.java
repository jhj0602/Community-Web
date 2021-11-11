package com.ssafy.community.post.dto;

import com.ssafy.community.post.entity.PostEntity;
import com.ssafy.community.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    @Positive(message = "사용자 없음")
    private Long userId;

    List<String> imageUrl;

    public PostEntity toSavePostEntity(UserEntity user) {
        PostEntity post = PostEntity.builder()
                .title(title)
                .content(content)
                .user(user)
                .imageUrl(imageUrl)
                .build();
        return post;
    }

}
