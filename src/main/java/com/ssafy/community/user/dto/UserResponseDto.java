package com.ssafy.community.user.dto;


import com.ssafy.community.user.entity.UserAuthority;
import com.ssafy.community.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private List<UserAuthority> authorities;

    public static UserResponseDto from(UserEntity userEntity) {
        return new UserResponseDto(userEntity.getId(), userEntity.getEmail(), userEntity.getAuthorities());
    }
}