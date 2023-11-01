package com.example.blogapi.controller;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.service.interfaces.AppUserService;
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

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AppUserController.class)
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    private AppUserDto appUserDto;

    @BeforeEach
    public void setUp() {
        appUserDto = AppUserDto.builder().id(0L).name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
    }

    @Test
    public void AppUserController_getAllUsers_ReturnsIsOk() throws Exception {
        when(appUserService.getAllUsers()).thenReturn(Arrays.asList(appUserDto, appUserDto));

        ResultActions response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(appUserDto, appUserDto).size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AppUserController_CreateAppUser_ReturnsIsCreated() throws Exception {
        when(appUserService.createUser(Mockito.any(AppUserDto.class))).thenReturn(appUserDto);

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(appUserDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(appUserDto.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(appUserDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(appUserDto.getPassword())));
    }

    @Test
    public void AppUserController_GetAppUserByID_ReturnsIsOk() throws Exception {
        when(appUserService.getUserById(Mockito.anyLong())).thenReturn(appUserDto);

        ResultActions response = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(appUserDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(appUserDto.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(appUserDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(appUserDto.getPassword())));
    }

    @Test
    public void AppUserController_GetAppUserByUsername_ReturnsIsOk() throws Exception {
        when(appUserService.getUserByUsername(Mockito.anyString())).thenReturn(appUserDto);

        ResultActions response = mockMvc.perform(get("/users/ayman00")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(appUserDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(appUserDto.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(appUserDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(appUserDto.getPassword())));
    }

    @Test
    public void AppUserController_UpdateAppUser_ReturnsIsOk() throws Exception {
        when(appUserService.updateUser(Mockito.anyLong(), Mockito.any(AppUserDto.class))).thenReturn(appUserDto);

        ResultActions response = mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(appUserDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(appUserDto.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(appUserDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(appUserDto.getPassword())));
    }

    @Test
    public void AppUserController_DeleteAppUser_ReturnsIsOk() throws Exception {
        when(appUserService.deleteUserById(Mockito.anyLong())).thenReturn(appUserDto.getId().intValue());

        ResultActions response = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(appUserDto.getId().intValue())));
    }


}