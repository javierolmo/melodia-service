package com.javi.uned.pfgbackend.adapters.api.roles;

import com.javi.uned.pfgbackend.adapters.api.roles.model.RoleDTO;
import com.javi.uned.pfgbackend.adapters.api.roles.model.RoleRequest;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RoleController {

    @GetMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RoleDTO> findAllRoles();

    @PostMapping(value = "/api/roles")
    RoleDTO createRole(@RequestBody RoleRequest roleRequest);

    @GetMapping(value = "/api/roles/{id}")
    RoleDTO getRole(@PathVariable Long id) throws EntityNotFound;

    @DeleteMapping(value = "/api/roles/{id}")
    void deleteRole(@PathVariable Long id) throws EntityNotFound;
}
