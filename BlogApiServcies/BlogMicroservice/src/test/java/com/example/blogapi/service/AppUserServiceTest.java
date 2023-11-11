package com.example.blogapi.service;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.service.impls.AppUserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AppUserServiceTest {

    long APP_USER_ID = 1;
    String APP_USER_USERNAME = "Ayman";
    @Mock
    private AppUserRepo appUserRepo;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    private AppUser appUser;
    private AppUserDto appUserDto;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        appUserService = new AppUserServiceImpl(modelMapper, appUserRepo);
        appUser = AppUser.builder().name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        appUserDto = AppUserDto.builder().name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
    }

    @Test
    public void AppUserService_CreateAppUser_ReturnAppUserDto() {
        when(appUserRepo.save(Mockito.any(AppUser.class))).thenReturn(appUser);

        AppUserDto savedUser = appUserService.createUser(appUserDto);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void AppUserService_GetAppUserById_ReturnsAppUserDto() {
        when(appUserRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(appUser));

        AppUserDto foundedUser = appUserService.getUserById(APP_USER_ID);

        Assertions.assertThat(foundedUser).isNotNull();
    }

    @Test
    public void AppUserService_GetAllAppUser_ReturnsMoreThanOneAppUser() {
        when(appUserRepo.findAll()).thenReturn(Arrays.asList(appUser, appUser));

        List<AppUserDto> appUsers = appUserService.getAllUsers();

        Assertions.assertThat(appUsers).isNotEmpty();
    }

    @Test
    public void AppUserService_GetAppUserByUsername_ReturnsAppUserDto() {
        when(appUserRepo.findAppUserByUsername(Mockito.anyString())).thenReturn(Optional.ofNullable(appUser));

        AppUserDto foundedUser = appUserService.getUserByUsername(APP_USER_USERNAME);

        Assertions.assertThat(foundedUser).isNotNull();
    }

    @Test
    public void AppUserService_UpdateAppUser_ReturnsAppUserDto() {
        when(appUserRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(appUser));
        when(appUserRepo.save(Mockito.any(AppUser.class))).thenReturn(appUser);

        AppUserDto updatedUser = appUserService.updateUser(APP_USER_ID, appUserDto);

        Assertions.assertThat(updatedUser).isNotNull();
    }

    @Test
    public void AppUserService_DeleteAppUser_ReturnsAppUserDto() {
        when(appUserRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(appUser));
        int deletedId = appUserService.deleteUserById(APP_USER_ID);
        assertAll(() -> appUserService.deleteUserById(APP_USER_ID));
        assertEquals(APP_USER_ID, deletedId);
    }
}