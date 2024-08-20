package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ditapro.dto.JwtTokenDto;
import com.example.ditapro.dto.LoginDto;
import com.example.ditapro.dto.UserDto;
import com.example.ditapro.model.User;
import com.example.ditapro.security.JwtService;
import com.example.ditapro.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        UserDto savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> loginUser(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("invalid email or password");
        }

        String token = jwtService.generateToken(loginDto.getEmail());

        return new ResponseEntity<>(new JwtTokenDto(token), HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<JwtTokenDto> confirmEmail(@RequestParam("token") String token) {
        String verified_token = userService.confirmEmail(token);
        return new ResponseEntity<>(new JwtTokenDto(verified_token), HttpStatus.OK);
    }
}
