package com.example.movieproject.service;

import com.example.movieproject.dto.PostDto;
import com.example.movieproject.entity.Post;
import com.example.movieproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // 게시글 작성
    public void write(PostDto postDto) {
        Post post = Post.builder()
                .userId(postDto.getUserId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        postRepository.save(post);
    }

    // 게시글 목록 조회
    public List<PostDto> getAll() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostDto postDto = new PostDto();
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setUserId(post.getUserId());
            postDto.setCnt(post.getCnt());
            postDto.setCreated(post.getCreated());
            postDto.setHeart(post.getHeart());
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    // 게시글 상세 조회 및 조회수 증가
    public PostDto getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // DTO 변환
            PostDto postDto = new PostDto();
            postDto.setUserId(post.getUserId());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setCnt(post.getCnt());
            postDto.setCreated(post.getCreated());
            postDto.setHeart(post.getHeart());

            // 조회수 증가
            post.setCnt(post.getCnt() + 1);
            postRepository.save(post);

            return postDto;
        } else {
            throw new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id);
        }
    }

    // 게시글 수정
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));
        postRepository.delete(post);
    }

    // 페이징 처리
    public Page<PostDto> getPostsByPage(PageRequest pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(this::convertToDto);
    }

    // 제목으로 검색
    public Page<PostDto> searchByTitle(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContaining(keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // 내용으로 검색
    public Page<PostDto> searchByContent(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByContentContaining(keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // 제목 또는 내용으로 검색
    public Page<PostDto> searchPosts(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // Post 엔티티를 PostDto로 변환하는 메서드
    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setUserId(post.getUserId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCnt(post.getCnt());
        dto.setCreated(post.getCreated());
        dto.setHeart(post.getHeart());
        return dto;
    }
}
