package com.javi.uned.pfgbackend.adapters.api.roles;

import com.javi.uned.pfgbackend.adapters.api.roles.model.RoleDTO;
import com.javi.uned.pfgbackend.adapters.api.roles.model.RoleDTOTransformer;
import com.javi.uned.pfgbackend.adapters.api.roles.model.RoleRequest;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.user.RoleService;
import com.javi.uned.pfgbackend.domain.user.model.Role;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Api(tags = "Roles")
public class RoleControllerImpl implements RoleController {

    @Autowired
    private RoleService roleService;

    @Override
    public List<RoleDTO> findAllRoles() {

        List<Role> roles = roleService.findAll();
        List<RoleDTO> roleDTOs = roles.stream().map(RoleDTOTransformer::toTransferObject).collect(Collectors.toList());

        return roleDTOs;
    }

    @Override
    public RoleDTO createRole(RoleRequest roleRequest) {

        Role role = roleService.createRoleIfNotFound(roleRequest.getName());
        RoleDTO roleDTO = RoleDTOTransformer.toTransferObject(role);

        return roleDTO;
    }

    @Override
    public RoleDTO getRole(Long id) throws EntityNotFound {

        Role role = roleService.findById(id);
        RoleDTO roleDTO = RoleDTOTransformer.toTransferObject(role);

        return roleDTO;
    }

    @Override
    public void deleteRole(Long id) throws EntityNotFound {
        roleService.deleteRole(id);
    }
}
