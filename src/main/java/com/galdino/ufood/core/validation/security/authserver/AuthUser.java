package com.galdino.ufood.core.validation.security.authserver;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {
    private Long userId;
    private String fullName;

    public AuthUser(com.galdino.ufood.domain.model.User domainUser, Collection<? extends GrantedAuthority> authorities) {
        super(domainUser.getEmail(), domainUser.getPassword(), authorities);

        this.userId = domainUser.getId();
        this.fullName = domainUser.getName();
    }
}
