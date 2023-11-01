package com.example.blogapi.controller;

import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;
import com.example.blogapi.service.interfaces.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("")
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<BlogDto> getBlogByIdentifier(@PathVariable("identifier") String identifier) {

        if (identifier.matches("\\d+")) {
            Long id = Long.valueOf(identifier);
            return ResponseEntity.ok(blogService.getBlogById(id));
        } else {
            return ResponseEntity.ok(blogService.getBlogBySlug(identifier));
        }

    }

    @PostMapping("")
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogRequestDto blog) {
        return ResponseEntity.ok(blogService.createBlog(blog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto blog) {
        return ResponseEntity.ok(blogService.updateBlog(id, blog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBlog(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.deleteBlogById(id));
    }
}
