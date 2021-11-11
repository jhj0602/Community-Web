package com.ssafy.community.post.dto;

import com.ssafy.community.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {
    @Id
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    List<String> imageUrl;

    public PostEntity toUpdatePostEntity() {
        PostEntity post = PostEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
        return post;
    }
}
