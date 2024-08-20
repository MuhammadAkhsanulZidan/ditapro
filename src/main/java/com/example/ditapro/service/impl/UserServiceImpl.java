package com.example.ditapro.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.ditapro.dto.UserDto;
import com.example.ditapro.model.InactiveUser;
import com.example.ditapro.model.User;
import com.example.ditapro.repository.InactiveUserRepository;
import com.example.ditapro.repository.UserRepository;
import com.example.ditapro.security.JwtService;
import com.example.ditapro.service.EmailService;
import com.example.ditapro.service.ImageService;
import com.example.ditapro.service.UserService;
import com.example.ditapro.util.App;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private InactiveUserRepository inactiveUserRepository;

    @Override
    public UserDto registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // String token = jwtService.generateToken(user.getEmail());

        // String confirmationUrl = App.WEB_URL+"/users/confirm?token="+token;

        // emailService.sendEmail(user.getEmail(), "Email Confirmation", confirmationUrl);

        User savedUser = userRepository.save(user);

        InactiveUser inactiveUser = new InactiveUser();
        inactiveUser.setUser(savedUser);
        inactiveUserRepository.save(inactiveUser);
        
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Transactional
    @Override
    public String confirmEmail(String token){
        if(!jwtService.validateToken(token)){
            throw new BadCredentialsException("invalid token");
        }

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        inactiveUserRepository.deleteByUserId(user.getId());
        return token;
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size)  {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        List<UserDto> userDtos = new ArrayList<>();
        for(User user:users.getContent()){
            userDtos.add(modelMapper.map(user, UserDto.class));
        }
        return userDtos;        
    }

    @Override
    public UserDto getUserByUuid(UUID uuid)  {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UUID uuid, Map<String, Object> updates, MultipartFile image) {
        Optional<User> optionalUser = userRepository.findByUuid(uuid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        user.setName((String) value);
                        break;
                    case "email":
                        user.setEmail((String) value);
                        break;
                    case "password":
                        user.setPassword(passwordEncoder.encode((String) value));
                        break;
                }
            });

            String oldFileImage = user.getImage();
                
            if (image != null && !image.isEmpty()){
                if(oldFileImage!=null && !oldFileImage.isEmpty()){
                    imageService.deleteImageFile(App.USER_STRING, uuid, oldFileImage);
                }
                String imageUrl = imageService.uploadImageFile(App.USER_STRING, uuid, image);
                user.setImage(imageUrl);
            }

            else {
                user.setImage(imageService.createImageFromName(updates.get("color").toString(), user.getName()));
                if(oldFileImage!=null && !oldFileImage.isEmpty()){
                    imageService.deleteImageFile(App.USER_STRING, uuid, oldFileImage);
                }
            }

            User patchedUser = userRepository.save(user);

            return modelMapper.map(patchedUser, UserDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }

    @Override
    public void deleteUser(UUID uuid)  {
        userRepository.deleteByUuid(uuid);
    }
    
    @Override
    public Resource getImage(UUID userUuid, String filename) {
        return imageService.getImageFile(App.USER_STRING, userUuid, filename);
    }

    @Override
    public List<UserDto> findUsersByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUsersByEmail'");
    }
}
