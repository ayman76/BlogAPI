package com.example.blogapi.service.impls;

import com.example.blogapi.model.AppUser;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public AppUser getUserById(Long id) {
        Optional<AppUser> foundedUser = appUserRepo.findById(id);
        if (foundedUser.isPresent()) {
            return foundedUser.get();
        }

        throw new RuntimeException("Not Founded User with id " + id);
    }

    @Override
    public List<AppUser> getAllUsers() {

        return appUserRepo.findAll();
    }

    @Override
    public AppUser createUser(AppUser user) {
        return appUserRepo.save(user);
    }

    @Override
    public AppUser updateUser(Long id, AppUser user) {
        Optional<AppUser> foundedUser = appUserRepo.findById(id);
        if (foundedUser.isPresent()) {
            AppUser updatedUser = foundedUser.get();
            updatedUser.setName(user.getName());
            return appUserRepo.save(updatedUser);
        }
        throw new RuntimeException("Not Founded User with id " + id);
    }

    @Override
    public int deleteUserById(Long id) {
        Optional<AppUser> foundedUser = appUserRepo.findById(id);
        if (foundedUser.isPresent()) {
            appUserRepo.deleteById(id);
            return 1;
        }
        throw new RuntimeException("Not Founded User with id " + id);
    }
}
