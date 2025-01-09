package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>,
        PagingAndSortingRepository<Post, Long> {

    // 제목으로 검색
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    // 내용으로 검색
    Page<Post> findByContentContaining(String keyword, Pageable pageable);

    // 제목 또는 내용으로 검색 메서드 수정
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:titleKeyword% OR p.content LIKE %:contentKeyword%")
    Page<Post> findByTitleContainingOrContentContaining(
            @Param("titleKeyword") String titleKeyword,
            @Param("contentKeyword") String contentKeyword,
            Pageable pageable
    );

    Page<Post> findByUserId(long userId, Pageable pageable);
}
