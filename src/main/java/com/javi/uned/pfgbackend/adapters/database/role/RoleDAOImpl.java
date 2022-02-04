package com.javi.uned.pfgbackend.adapters.database.role;

import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.ports.database.RoleDAO;
import com.javi.uned.pfgbackend.domain.user.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {

        List<RoleEntity> roleEntities = roleRepository.findAll();

        return roleEntities.stream()
                .map(RoleEntityTransformer::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public Role findByName(String name) throws EntityNotFound {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
            throw new EntityNotFound("Could not find role with name '" + name + "'");
        } else {
            return RoleEntityTransformer.toDomainObject(roleEntity);
        }
    }

    @Override
    public Role createRoleIfNotFound(String name) {

        RoleEntity roleEntity = roleRepository.findByName(name);

        if (roleEntity == null) {
            roleEntity = new RoleEntity();
            roleEntity.setName(name);
            roleEntity = roleRepository.save(roleEntity);
            return RoleEntityTransformer.toDomainObject(roleEntity);
        } else {
            return RoleEntityTransformer.toDomainObject(roleEntity);
        }
    }

    @Override
    public Role findById(Long id) throws EntityNotFound {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
        if (roleEntityOptional.isPresent()) {
            return RoleEntityTransformer.toDomainObject(roleEntityOptional.get());
        } else {
            throw new EntityNotFound("Could not find role with id '" + id + "'");
        }

    }

    @Override
    public void delete(Long id) throws EntityNotFound {
        if(roleRepository.existsById(id)){
            roleRepository.deleteById(id);
        }else {
            throw new EntityNotFound("Role with id '"+id+"' does not exist");
        }
    }

}
