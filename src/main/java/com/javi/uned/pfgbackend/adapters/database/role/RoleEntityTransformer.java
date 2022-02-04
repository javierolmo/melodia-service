package com.javi.uned.pfgbackend.adapters.database.role;

import com.javi.uned.pfgbackend.domain.user.model.Role;

public class RoleEntityTransformer {

    private RoleEntityTransformer() {

    }

    public static Role toDomainObject(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(), roleEntity.getName());
    }

    public static RoleEntity toEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setName(role.getName());
        return roleEntity;
    }

}
