package com.javi.uned.pfgbackend.config.database;

import com.javi.uned.pfgbackend.domain.exceptions.ExistingUserException;
import com.javi.uned.pfgbackend.domain.exceptions.ValidationException;
import com.javi.uned.pfgbackend.domain.user.RoleService;
import com.javi.uned.pfgbackend.domain.user.UserService;
import com.javi.uned.pfgbackend.domain.user.model.Role;
import com.javi.uned.pfgbackend.domain.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(SetupDataLoader.class);
    boolean alreadySetup = false;
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        // Declare and create roles
        Role adminRole = roleService.createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = roleService.createRoleIfNotFound("ROLE_USER");
        Role testerRole = roleService.createRoleIfNotFound("ROLE_TESTER");


        // Declare users
        User javi = new User(null, "jolmo60@alumno.uned.es", "1234", "Javier", "Olmo Injerto", true, Arrays.asList(adminRole));
        User tester = new User(null, "tester@gmail.com", "1234", "Tester", "Appelido1 Apellido2", true, Arrays.asList(testerRole));
        User joseManuel = new User(null, "jmcuadra@dia.uned.es", "1234", "Jose Manuel", "Cuadra Troncoso", true, Arrays.asList(userRole));
        User[] users = new User[]{javi, tester, joseManuel};

        // Create users
        for (User user : users) {
            try {
                userService.registerUser(user);
            } catch (ValidationException | ExistingUserException e) {
                logger.debug("Omitting user creation: " + user.getEmail(), e);
            }
        }

        alreadySetup = true;
    }

}
