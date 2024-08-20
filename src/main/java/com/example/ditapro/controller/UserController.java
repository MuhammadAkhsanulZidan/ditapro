package com.example.ditapro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ditapro.dto.CourseDto;
import com.example.ditapro.dto.CourseEnrollmentDto;
import com.example.ditapro.dto.UserDto;
import com.example.ditapro.model.User;
import com.example.ditapro.security.CustomUserDetails;
import com.example.ditapro.service.CourseEnrollmentService;
import com.example.ditapro.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserDto savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByUuid(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UUID uuid = userDetails.getUuid();
        UserDto userDTO = userService.getUserByUuid(uuid);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchUser(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "color", required = false) String color) {
        UUID uuid = userDetails.getUuid();

        Map<String, Object> updates = new HashMap<>();
        if (name != null)
            updates.put("name", name);
        if (email != null)
            updates.put("email", email);
        if (color != null)
            updates.put("color", color);

        UserDto patchedUser = userService.updateUser(uuid, updates, image);
        return new ResponseEntity<>(patchedUser, HttpStatus.OK);
    }

    @GetMapping("/images/{userUuid}/{filename:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable UUID userUuid,
            @PathVariable String filename) {
        Resource fileResource = userService.getImage(userUuid, filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(fileResource);
    }

    @GetMapping("/all")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<UserDto> users = userService.getAllUsers(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/enrollments")
    public List<CourseEnrollmentDto> getEnrollmentsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UUID userUuid = userDetails.getUuid();
        return courseEnrollmentService.findEnrollmentsByUserUuid(userUuid);
    }

}
