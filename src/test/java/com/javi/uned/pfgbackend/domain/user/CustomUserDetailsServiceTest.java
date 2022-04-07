package com.javi.uned.pfgbackend.domain.user;

import com.javi.uned.pfgbackend.adapters.database.role.RoleEntity;
import com.javi.uned.pfgbackend.adapters.database.role.RoleEntityTransformer;
import com.javi.uned.pfgbackend.adapters.database.user.UserEntity;
import com.javi.uned.pfgbackend.adapters.database.user.UserRepository;
import com.javi.uned.pfgbackend.domain.user.model.Role;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Resource
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeAll
    void setUp() {

    }

    @AfterAll
    void tearDown() {
    }

    @Test
    void loadUserByUsername_success() {

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(RoleEntityTransformer.toEntity(new Role(1L, "TestRole1")));
        roles.add(RoleEntityTransformer.toEntity(new Role(2L, "TestRole2")));

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("olmo.injerto.javier@gmail.com");
        userEntity.setPassword("1234");
        userEntity.setRoles(roles);

        MockitoAnnotations.initMocks(this);
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("example@gmail.com");

        assertEquals(userDetails.getUsername(), userEntity.getEmail());
        assertEquals(userDetails.getAuthorities(), userEntity.getRoles());
        assertEquals(userDetails.getPassword(), userEntity.getPassword());

    }

    @Test
    void loadUserByUsername_UserNotFound() {

        MockitoAnnotations.initMocks(this);
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("example@gmail.com"));

    }
}