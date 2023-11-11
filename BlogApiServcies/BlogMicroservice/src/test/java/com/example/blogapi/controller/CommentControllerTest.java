package com.example.blogapi.controller;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.dto.comment.CommentDto;
import com.example.blogapi.dto.comment.CommentRequestDto;
import com.example.blogapi.service.interfaces.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    private static final String URL = "/api/v1/blog/1/comments";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentRequestDto commentRequestDto;
    private CommentDto commentDto;

    private List<CommentDto> commentDtos;

    @BeforeEach
    public void setUp() {
        commentDtos = new ArrayList<>();
        AppUserDto appUserDto = AppUserDto.builder().id(1L).name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        commentDto = CommentDto.builder().id(1L).body("comment").appUser(appUserDto).build();
        commentRequestDto = CommentRequestDto.builder().body("comment").userId(appUserDto.getId()).build();
        commentDtos.addAll(Arrays.asList(commentDto, commentDto));
    }

    @Test
    public void CommentController_CreateComment_ReturnIsOk() throws Exception {
        when(commentService.createComment(Mockito.anyLong(), Mockito.any(CommentRequestDto.class))).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(commentRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CommentController_GetCommentById_ReturnIsOk() throws Exception {
        when(commentService.getCommentById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(commentDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body", CoreMatchers.is(commentDto.getBody())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CommentController_GetAllComments_ReturnIsOk() throws Exception {
        when(commentService.getAllComments(Mockito.anyLong())).thenReturn(commentDtos);

        ResultActions response = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(commentDtos.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CommentController_UpdateComment_ReturnIsOk() throws Exception {
        when(commentService.updateComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(CommentRequestDto.class))).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(commentRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body", CoreMatchers.is(commentDto.getBody())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CommentController_DeleteComment_ReturnIsOk() throws Exception {
        when(commentService.deleteComment(Mockito.anyLong(), Mockito.anyLong())).thenReturn(commentDto.getId().intValue());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(commentDto.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

}