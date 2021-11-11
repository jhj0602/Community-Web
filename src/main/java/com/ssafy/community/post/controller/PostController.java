package com.ssafy.community.post.controller;

import com.ssafy.community.post.dto.PostRequestDto;
import com.ssafy.community.post.dto.PostResponseDto;
import com.ssafy.community.post.dto.PostUpdateRequestDto;
import com.ssafy.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/detail/user/{id}")
    public ResponseEntity<List<PostResponseDto>> findByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.findByUserId(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PostMapping("/create/images")
    public ResponseEntity<List<String>> imageSave(@RequestPart List<MultipartFile> images) {
        return ResponseEntity.ok((postService.postS3ImageSave(images)));
    }

    @PostMapping("/create")
    public ResponseEntity<String> save(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.save(postRequestDto);
        return ResponseEntity.created(URI.create("/" + postResponseDto.getId()))
                .build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        PostResponseDto postResponseDto = postService.update(postUpdateRequestDto);
        return ResponseEntity.created(URI.create("/" + postResponseDto.getId()))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
