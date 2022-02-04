package com.javi.uned.pfgbackend.adapters.api.roles.model;

import com.javi.uned.pfgbackend.domain.user.model.Role;

public class RoleDTOTransformer {

    private RoleDTOTransformer() {

    }

    public static Role toDomainObject(RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getName());
    }

    public static RoleDTO toTransferObject(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
