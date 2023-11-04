package com.example.blogapi.controller;

import com.example.blogapi.dto.TagDto;
import com.example.blogapi.service.interfaces.TagService;
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

@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TagControllerTest {
    private static final String URL = "/api/v1/tags";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    private TagDto tagDto;
    private List<TagDto> tagDtos;

    @BeforeEach
    public void setUp() {
        tagDto = TagDto.builder().id(1L).name("tag").build();
        tagDtos = new ArrayList<>();
        tagDtos.addAll(Arrays.asList(tagDto, tagDto));
    }

    @Test
    public void TagController_CreateTag_ReturnIsOk() throws Exception {
        when(tagService.createTag(Mockito.any(TagDto.class))).thenReturn(tagDto);

        ResultActions response = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tagDto.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TagController_GetTagById_ReturnIsOk() throws Exception {
        when(tagService.getTagById(Mockito.anyLong())).thenReturn(tagDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tagDto.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TagController_GetAllTags_ReturnIsOk() throws Exception {
        when(tagService.getAllTags()).thenReturn(tagDtos);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(tagDtos.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TagController_UpdateTag_ReturnIsOk() throws Exception {
        when(tagService.updateTag(Mockito.anyLong(), Mockito.any(TagDto.class))).thenReturn(tagDto);

        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tagDto.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TagController_DeleteTag_ReturnIsOk() throws Exception {
        when(tagService.deleteTag(Mockito.anyLong())).thenReturn(tagDto.getId().intValue());

        ResultActions response = mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(tagDto.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }


}