package com.example.blogapi.service.impls;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.service.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final ModelMapper modelMapper;
    private final AppUserRepo appUserRepo;

    @Override
    public AppUserDto getUserById(Long id) {
        AppUser foundedUser = appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded User"));
        return modelMapper.map(foundedUser, AppUserDto.class);
    }

    @Override
    public List<AppUserDto> getAllUsers() {

        return appUserRepo.findAll().stream().map(u -> modelMapper.map(u, AppUserDto.class)).collect(Collectors.toList());
    }

    @Override
    public AppUserDto createUser(AppUserDto userDto) {
        AppUser appUser = modelMapper.map(userDto, AppUser.class);
        AppUser newUser = appUserRepo.save(appUser);

        return modelMapper.map(newUser, AppUserDto.class);

    }

    @Override
    public AppUserDto updateUser(Long id, AppUserDto userDto) {
        AppUser foundedUser = appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded User"));
        foundedUser.setName(checkNullAndReturn(userDto.getName(), foundedUser.getName()));
        foundedUser.setUsername(checkNullAndReturn(userDto.getUsername(), foundedUser.getUsername()));
        foundedUser.setEmail(checkNullAndReturn(userDto.getEmail(), foundedUser.getEmail()));
        foundedUser.setPassword(checkNullAndReturn(userDto.getPassword(), foundedUser.getPassword()));
        AppUser updatedUser = appUserRepo.save(foundedUser);
        return modelMapper.map(updatedUser, AppUserDto.class);
    }

    @Override
    public int deleteUserById(Long id) {
        AppUser foundedUser = appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded User"));
        appUserRepo.deleteById(foundedUser.getId());
        return id.intValue();

    }

    @Override
    public AppUserDto getUserByUsername(String username) {
        AppUser foundedUser = appUserRepo.findAppUserByUsername(username).orElseThrow(() -> new RuntimeException("Not Founded User"));
        return modelMapper.map(foundedUser, AppUserDto.class);
    }

    public String checkNullAndReturn(String checkedValue, String defaultValue) {
        return (checkedValue == null) ? defaultValue : checkedValue;
    }


}
