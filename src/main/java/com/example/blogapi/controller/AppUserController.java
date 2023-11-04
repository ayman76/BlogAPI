package com.example.blogapi.controller;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("")
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable("identifier") String identifier) {
        if (identifier.matches("\\d+")) {
            return ResponseEntity.ok(appUserService.getUserById(Long.valueOf(identifier)));
        } else {
            return ResponseEntity.ok(appUserService.getUserByUsername(identifier));
        }
    }

    @PostMapping("")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserDto appUser) {
        return ResponseEntity.ok(appUserService.createUser(appUser));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppUserDto> updateUser(@PathVariable Long id, @RequestBody AppUserDto appUser) {
        return ResponseEntity.ok(appUserService.updateUser(id, appUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.deleteUserById(id));
    }
}
