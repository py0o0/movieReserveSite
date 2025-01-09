package com.example.moviecommu.repository;

import com.example.moviecommu.entity.PostFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostFileRepository {
    void save(PostFile postFile);

    List<PostFile> findByPostId(Long postId);
}
