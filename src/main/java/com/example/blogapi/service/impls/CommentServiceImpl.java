package com.example.blogapi.service.impls;

import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Comment;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.CommentRepo;
import com.example.blogapi.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final BlogRepo blogRepo;
    private final AppUserRepo appUserRepo;

    @Override
    public Comment getCommentById(Long blogId, Long id) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow();
        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            return foundedComment;
        }
        throw new RuntimeException("Comment Does not Belong to Blog");
    }

    @Override
    public List<Comment> getAllComments(Long blogId) {
        Blog foundedBlog = blogRepo.findById(blogId).orElseThrow();
        return foundedBlog.getComments();
//        return commentRepo.findAll();
    }

    @Override
    @Transactional
    public Comment createComment(Long blogId, Comment comment) {
        Blog foundedBlog = blogRepo.findById(blogId).orElseThrow();
        AppUser appUser = appUserRepo.findById(comment.getUser().getId()).orElseThrow();
        comment.setBlog(foundedBlog);
        comment.setUser(appUser);
        return commentRepo.save(comment);
    }

    @Override
    @Transactional
    public int deleteComment(Long blogId, Long id) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Comment with id " + id));
        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            blog.getComments().remove(foundedComment);
            blogRepo.save(blog);
            return 1;
        }
        throw new RuntimeException("Not Founded Comment with id " + id);
    }

    @Override
    public Comment updateComment(Long blogId, Long id, Comment comment) {
        Blog blog = blogRepo.findById(blogId).orElseThrow();
        Comment foundedComment = commentRepo.findById(id).orElseThrow();

        if (foundedComment.getBlog().getId().equals(blog.getId())) {
            foundedComment.setBody(comment.getBody());
            return commentRepo.save(foundedComment);
        }
        throw new RuntimeException("Not Founded Comment with id " + id);
    }

}
