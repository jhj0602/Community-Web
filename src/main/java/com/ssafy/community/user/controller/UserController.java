package com.ssafy.community.user.controller;


import com.ssafy.community.user.annotation.PreAuthorize;
import com.ssafy.community.user.dto.*;
import com.ssafy.community.user.entity.UserEntity;
import com.ssafy.community.user.jwt.AuthInterceptor;
import com.ssafy.community.user.jwt.TokenProvider;
import com.ssafy.community.user.service.UserManagementService;
import com.ssafy.community.user.service.UserSignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.ssafy.community.user.jwt.AuthInterceptor.TOKEN_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserSignupService userSignupService;
    private final UserManagementService userManagementService;
    private final TokenProvider tokenProvider;

    @GetMapping("/emails/{email}/exists")
    public ResponseEntity<Boolean> isDuplicatedEmail(@PathVariable String email) {
        return new ResponseEntity(userSignupService.validateDuplicatedEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignupDto userSignupDto) throws Exception {
        UserResponseDto savedUser = userSignupService.save(userSignupDto);
        return ResponseEntity
                .created(URI.create("/" + savedUser.getId()))
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/create/images")
    public ResponseEntity<String> imageSave(@RequestParam("images")MultipartFile images) throws IOException {
        return ResponseEntity.ok((userManagementService.userS3ImageSave(images)));
    }

    @GetMapping("/{id}/details")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<UserResponseDto> details(@PathVariable Long id) {
        UserResponseDto userDetails = userManagementService.findById(id);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        UserEntity userEntity = userManagementService.login(userLoginDto);
        String jwt = tokenProvider.createToken(userEntity);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AuthInterceptor.AUTHORIZATION_HEADER, TOKEN_HEADER + jwt);
        return new ResponseEntity<>(new UserTokenDto(userEntity.getId(), jwt), httpHeaders, HttpStatus.OK);
    }
}