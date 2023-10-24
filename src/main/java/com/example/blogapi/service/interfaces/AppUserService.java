package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser getUserById(Long id);

    List<AppUser> getAllUsers();

    AppUser createUser(AppUser user);

    AppUser updateUser(Long id, AppUser user);

    int deleteUserById(Long id);

    AppUser getUserByUsername(String username);
}
