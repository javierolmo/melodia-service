package com.javi.uned.pfgbackend.domain.ports.database;

import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.user.model.Role;

import java.util.List;

public interface RoleDAO {

    List<Role> findAll();

    Role findByName(String name) throws EntityNotFound;

    Role createRoleIfNotFound(String name);

    Role findById(Long id) throws EntityNotFound;

    void delete(Long id) throws EntityNotFound;
}
