package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 댓글 데이터 접근 인터페이스 - 데이터베이스 연산을 위한 메서드 제공
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}

