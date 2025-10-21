package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Post;
import com.example.moviecommu.entity.PostHeart;
import com.example.moviecommu.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PostHeart findByPostAndUser(Post post, User user);

    Page<PostHeart> findByUser(User user, Pageable pageable);
}
