package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(Long blogId, Long id);

    List<Comment> getAllComments(Long blogId);

    Comment createComment(Long blogId, Comment comment);

    Comment updateComment(Long blogId, Long id, Comment comment);

    int deleteComment(Long blogId, Long id);
}
