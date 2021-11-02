package com.ssafy.community.user.dto;


import com.ssafy.community.user.entity.UserEntity;
import com.ssafy.community.user.exception.UnmatchedPasswordCheckException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    @Id
    private Long id;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 확인하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String password;

    @NotBlank(message = "비밀번호를 한 번 더 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String passwordCheck;

    public UserEntity toUserEntity() {
        validateSamePassword(password, passwordCheck);
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .build();

        return userEntity;
    }

    public void validateSamePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new UnmatchedPasswordCheckException();
        }
    }
}