package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.authservice.entity.SecurityUser;
import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.core.user.SecurityUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserResponse userResponse = userClient.getUserByUsername(username).getBody();
        return SecurityUser.from(userResponse);
    }
}
