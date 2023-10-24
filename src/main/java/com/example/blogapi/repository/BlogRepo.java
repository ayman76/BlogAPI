package com.example.blogapi.repository;

import com.example.blogapi.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Long> {
    Optional<Blog> findBlogBySlug(String slug);
}
