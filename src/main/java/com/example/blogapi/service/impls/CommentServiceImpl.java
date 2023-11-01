package com.example.blogapi.service.impls;

import com.example.blogapi.dto.comment.CommentDto;
import com.example.blogapi.dto.comment.CommentRequestDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Comment;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.CommentRepo;
import com.example.blogapi.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepo commentRepo;
    private final BlogRepo blogRepo;
    private final AppUserRepo appUserRepo;

    @Override
    public CommentDto getCommentById(Long blogId, Long id) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Comment with id : " + id));
        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            return modelMapper.map(foundedComment, CommentDto.class);
        }
        throw new RuntimeException("CommentDto Does not Belong to Blog");
    }

    @Override
    public List<CommentDto> getAllComments(Long blogId) {
        Blog foundedBlog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Not Founed Blog with id: " + blogId));
        return foundedBlog.getComments().stream().map(c -> modelMapper.map(c, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto createComment(Long blogId, CommentRequestDto commentDto) {
        Blog foundedBlog = blogRepo.findById(blogId).orElseThrow();
        AppUser appUser = appUserRepo.findById(commentDto.getUserId()).orElseThrow();
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setBlog(foundedBlog);
        comment.setUser(appUser);
        Comment newComment = commentRepo.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    @Transactional
    public int deleteComment(Long blogId, Long id) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Comment with id " + id));
        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            blog.getComments().remove(foundedComment);
            blogRepo.save(blog);
            return id.intValue();
        }
        throw new RuntimeException("CommentDto Does not Belong to Blog");
    }

    @Override
    public CommentDto updateComment(Long blogId, Long id, CommentRequestDto commentDto) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Comment with id " + id));

        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            foundedComment.setBody(commentDto.getBody());
            Comment updatedComment = commentRepo.save(foundedComment);
            return modelMapper.map(updatedComment, CommentDto.class);
        }
        throw new RuntimeException("Not Founded CommentDto with id " + id);
    }


}
