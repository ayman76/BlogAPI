package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.Blog;

import java.util.List;

public interface BlogService {

    Blog getBlogById(Long id);

    List<Blog> getAllBlogs();

    Blog createBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog) throws RuntimeException;

    int deleteBlogById(Long id);

    Blog getBlogBySlug(String slug);


}
