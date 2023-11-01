package com.example.blogapi.service;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.dto.comment.CommentDto;
import com.example.blogapi.dto.comment.CommentRequestDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Category;
import com.example.blogapi.model.Comment;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.CommentRepo;
import com.example.blogapi.service.impls.CommentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private final Long BLOG_ID = 1L;
    private final Long COMMENT_ID = 1L;
    @Mock
    private CommentRepo commentRepo;

    @Mock
    private BlogRepo blogRepo;

    @Mock
    private AppUserRepo appUserRepo;

    @InjectMocks
    private CommentServiceImpl commentService;

    private AppUser appUser;
    private Blog blog;
    private Comment comment;

    private CommentRequestDto commentRequestDto;

    private List<Comment> comments;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        comments = new ArrayList<>();
        commentService = new CommentServiceImpl(modelMapper, commentRepo, blogRepo, appUserRepo);
        appUser = AppUser.builder().name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        AppUserDto appUserDto = AppUserDto.builder().id(1L).name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        Category category = Category.builder().name("category").slug("category-slug").build();
        blog = Blog.builder().id(BLOG_ID).title("blog").description("blog description").slug("blog-slug").user(appUser).category(category).build();
        comment = Comment.builder().id(COMMENT_ID).body("comment body").user(appUser).blog(blog).build();
        commentRequestDto = CommentRequestDto.builder().body("comment body").userId(appUserDto.getId()).build();
        comments.addAll(Arrays.asList(comment, comment));
        blog.setComments(comments);

    }

    @Test
    public void CommentService_CreateComment_ReturnCommentDto() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));
        when(appUserRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(appUser));
        when(commentRepo.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto savedComment = commentService.createComment(BLOG_ID, commentRequestDto);

        Assertions.assertThat(savedComment).isNotNull();
    }

    @Test
    public void CommentService_GetCommentById_ReturnCommentDto() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));
        when(commentRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(comment));

        CommentDto foundedComment = commentService.getCommentById(BLOG_ID, COMMENT_ID);

        Assertions.assertThat(foundedComment).isNotNull();
    }

    @Test
    public void CommentService_GetAllComments_ReturnCommentDtos() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));

        List<CommentDto> commentsDtos = commentService.getAllComments(BLOG_ID);

        Assertions.assertThat(commentsDtos).isNotEmpty();
        Assertions.assertThat(commentsDtos.size()).isEqualTo(comments.size());
    }

    @Test
    public void CommentService_UpdateComment_ReturnCommentDto() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));
        when(commentRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(comment));
        when(commentRepo.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto updatedComment = commentService.updateComment(BLOG_ID, COMMENT_ID, commentRequestDto);

        Assertions.assertThat(updatedComment).isNotNull();
    }

    @Test
    public void CommentService_DeleteComment_ReturnCommentId() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));
        when(commentRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(comment));
        when(blogRepo.save(Mockito.any(Blog.class))).thenReturn(blog);

        int deletedCommentId = commentService.deleteComment(BLOG_ID, COMMENT_ID);

        Assertions.assertThat(deletedCommentId).isEqualTo(COMMENT_ID.intValue());
    }

}