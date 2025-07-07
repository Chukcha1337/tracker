package com.chuckcha.tt.authservice.entity;

import com.chuckcha.tt.core.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@ToString(exclude = "password")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SecurityUser implements UserDetails, CredentialsContainer {

    private Long id;
    private final String username;
    @JsonIgnore
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public static SecurityUser from(UserResponse user) {
        return SecurityUser.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .authorities(List.of(user.role()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
