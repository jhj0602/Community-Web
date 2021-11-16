package com.ssafy.community.user.service;


import com.ssafy.community.image.service.S3Uploader;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

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
        UserEntity user = userRepository.findById(id).orElseThrow(NoUserFoundException::new);
        userS3ProfileImageDelete(user.getProfileImage());
        userRepository.deleteById(id);
    }

    public void update(UserUpdateDto userUpdateDto) {
        UserEntity updateUser = userUpdateDto.toUserEntity();
        UserEntity originUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(NoUserFoundException::new);
        if (!originUser.getEmail().equals(userUpdateDto.getEmail())) {
            validateDuplicatedEmail(userUpdateDto.getEmail());
        }
        userS3ProfileImageDelete(originUser.getProfileImage());
        UserEntity user = UserEntity.builder()
                .id(updateUser.getId())
                .email(updateUser.getEmail())
                .password(passwordEncoder.encrypt(updateUser.getPassword()))
                .nickname(updateUser.getNickname())
                .profileImage(userUpdateDto.getProfileImage())
                .authorities(originUser.getAuthorities())
                .posts(originUser.getPosts())
                .build();
        userRepository.save(user);
    }

    public String userS3ImageSave(MultipartFile profileImage) throws IOException {
        return s3Uploader.upload(profileImage, "static");
        // return profileImage.getOriginalFilename();
    }

    public void userS3ProfileImageDelete(String profileImage) {
        s3Uploader.delete(profileImage);
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