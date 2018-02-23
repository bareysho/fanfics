package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.model.Role;
import by.bareysho.fanfics.repository.RoleRepository;
import by.bareysho.fanfics.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByRoleName(name);
    }
}
