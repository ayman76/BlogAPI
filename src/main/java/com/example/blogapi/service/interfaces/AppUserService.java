package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {
    AppUser getUserById(Long id);

    List<AppUser> getAllUsers();

    AppUser createUser(AppUser user);

    AppUser updateUser(Long id, AppUser user);

    int deleteUserById(Long id);
}
