package com.ssafy.community.user.service;


import com.ssafy.community.user.dto.UserLoginDto;
import com.ssafy.community.user.dto.UserResponseDto;
import com.ssafy.community.user.dto.UserUpdateDto;
import com.ssafy.community.user.encoder.BCryptPasswordEncoder;
import com.ssafy.community.user.entity.UserEntity;
import com.ssafy.community.user.exception.DuplicatedEmailException;
import com.ssafy.community.user.exception.NoUserFoundException;
import com.ssafy.community.user.exception.UnmatchedPasswordCheckException;
import com.ssafy.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return UserResponseDto.from(user.orElseThrow(NoUserFoundException::new));
    }

    public List<UserResponseDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoUserFoundException();
        }
        userRepository.deleteById(id);
    }

    public void update(UserUpdateDto userUpdateDto) {
        UserEntity updateUserEntity = userUpdateDto.toUserEntity();
        UserEntity originUserEntity = userRepository.findById(userUpdateDto.getId()).orElseThrow(NoUserFoundException::new);
        UserEntity userEntity = UserEntity.builder()
                .id(updateUserEntity.getId())
                .email(updateUserEntity.getEmail())
                .password(passwordEncoder.encrypt(updateUserEntity.getPassword()))
                .authorities(originUserEntity.getAuthorities())
                .build();

        if (!originUserEntity.getEmail().equals(userUpdateDto.getEmail())) {
            validateDuplicatedEmail(userUpdateDto.getEmail());
        }
        userRepository.save(userEntity);
    }

    public void validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    public UserEntity login(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository.findOneWithAuthoritiesByEmail(userLoginDto.getEmail())
                .orElseThrow(NoUserFoundException::new);
        if (passwordEncoder.isMatch(userLoginDto.getPassword(), userEntity.getPassword())) {
            return userEntity;
        }
        throw new UnmatchedPasswordCheckException();
    }
}