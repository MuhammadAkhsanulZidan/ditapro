package com.example.ditapro.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.example.ditapro.dto.UserDto;
import com.example.ditapro.model.User;


public interface UserService {
    public UserDto registerUser(User user);
    public String confirmEmail(String token);
    public List<UserDto>getAllUsers(int page, int size);
    public List<UserDto>findUsersByEmail(String email);
    public UserDto getUserByUuid(UUID uuid);
    public UserDto updateUser(UUID uuid, Map<String, Object> updates, MultipartFile image);
    public void deleteUser(UUID uuid);
    public Resource getImage(UUID userUuid, String filename);
}
