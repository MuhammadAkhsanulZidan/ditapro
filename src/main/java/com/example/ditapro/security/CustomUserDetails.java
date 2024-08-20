package com.example.ditapro.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ditapro.model.User;

public class CustomUserDetails implements UserDetails{

    private String username;
    private String password;
    private UUID uuid;
    private Long id;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Collections.emptyList();
        this.uuid = user.getUuid();
        this.id = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

}
