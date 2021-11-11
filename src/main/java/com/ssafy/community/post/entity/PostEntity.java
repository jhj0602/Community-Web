package com.ssafy.community.post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.community.time.BaseTimeEntity;
import com.ssafy.community.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostEntity  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_image", joinColumns = @JoinColumn(name = "post_id"))
    @JsonBackReference
    private List<String> imageUrl;
}
