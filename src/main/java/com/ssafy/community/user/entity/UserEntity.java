package com.ssafy.community.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference(value="user-userAuthority")
    private List<UserAuthority> authorities;
}