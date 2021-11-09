package com.ssafy.community.user.service;

import com.ssafy.community.image.service.S3Uploader;
import com.ssafy.community.user.dto.UserResponseDto;
import com.ssafy.community.user.dto.UserSignupDto;
import com.ssafy.community.user.encoder.BCryptPasswordEncoder;
import com.ssafy.community.user.entity.Authority;
import com.ssafy.community.user.entity.UserAuthority;
import com.ssafy.community.user.entity.UserEntity;
import com.ssafy.community.user.exception.DuplicatedEmailException;
import com.ssafy.community.user.repository.AuthorityRepository;
import com.ssafy.community.user.repository.UserAuthorityRepository;
import com.ssafy.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserSignupService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    @Transactional
    public UserResponseDto save(UserSignupDto userSignupDto, MultipartFile profileImages) throws Exception {
        if (!validateDuplicatedEmail(userSignupDto.getEmail())) {
            throw new DuplicatedEmailException();
        }
        UserEntity signupUser = userSignupDto.toUserEntity();
        UserEntity user = UserEntity.builder()
                .email(signupUser.getEmail())
                .password(passwordEncoder.encrypt(signupUser.getPassword()))
                .nickname(signupUser.getNickname())
                .profileImage(s3Uploader.upload(profileImages, "static"))
                .authorities(signupUser.getAuthorities())
                .build();

        Authority auth = authorityRepository.findById(2L).orElseThrow(Exception::new);
        UserAuthority userAuth = UserAuthority.builder()
                .userEntity(user)
                .auth(auth)
                .build();

        userAuthorityRepository.save(userAuth);
        return UserResponseDto.from(userRepository.save(user));
    }

    public boolean validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }
}