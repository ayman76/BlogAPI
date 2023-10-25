package com.example.blogapi.controller;

import com.example.blogapi.model.Comment;
import com.example.blogapi.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blog/{blogId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long blogId) {
        return ResponseEntity.ok(commentService.getAllComments(blogId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long blogId, @PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(blogId, id));
    }

    @PostMapping("")
    public ResponseEntity<Comment> createComment(@PathVariable Long blogId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.createComment(blogId, comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> createComment(@PathVariable Long blogId, @PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(blogId, id, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCommentById(@PathVariable Long blogId, @PathVariable Long id) {
        return ResponseEntity.ok(commentService.deleteComment(blogId, id));
    }

}
