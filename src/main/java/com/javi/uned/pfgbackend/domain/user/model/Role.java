package com.javi.uned.pfgbackend.domain.user.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority{

    private final Long id;
    private final String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
