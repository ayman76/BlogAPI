package com.example.blogapi.service.interfaces;

import com.example.blogapi.dto.comment.CommentDto;
import com.example.blogapi.dto.comment.CommentRequestDto;

import java.util.List;

public interface CommentService {
    CommentDto getCommentById(Long blogId, Long id);

    List<CommentDto> getAllComments(Long blogId);

    CommentDto createComment(Long blogId, CommentRequestDto commentDto);

    CommentDto updateComment(Long blogId, Long id, CommentRequestDto commentDto);

    int deleteComment(Long blogId, Long id);
}
