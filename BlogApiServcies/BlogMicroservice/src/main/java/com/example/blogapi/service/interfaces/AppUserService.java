package com.example.blogapi.service.interfaces;

import com.example.blogapi.dto.AppUserDto;

import java.util.List;

public interface AppUserService {
    AppUserDto getUserById(Long id);

    List<AppUserDto> getAllUsers();

    AppUserDto createUser(AppUserDto userDto);

    AppUserDto updateUser(Long id, AppUserDto userDto);

    int deleteUserById(Long id);

    AppUserDto getUserByUsername(String username);
}
