package com.ssafy.community.post.dto;

import com.ssafy.community.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImage;
    private List<String> postImageUrl;

    public static PostResponseDto from(PostEntity post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),
                post.getUser().getNickname(), post.getUser().getProfileImage(),post.getImageUrl());
    }
}
