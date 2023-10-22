package com.example.blogapi.controller;

import com.example.blogapi.model.AppUser;
import com.example.blogapi.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.getUserById(id));
    }

    @PostMapping("")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.createUser(appUser));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.updateUser(id, appUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.deleteUserById(id));
    }
}
