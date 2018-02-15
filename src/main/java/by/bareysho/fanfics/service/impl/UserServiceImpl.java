package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.repository.RoleRepository;
import by.bareysho.fanfics.repository.UserRepository;
import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Role;
import by.bareysho.fanfics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(CustomUser user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public CustomUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public CustomUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findByUsername(name);
    }

    @Override
    public CustomUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public CustomUser findByPassword(String password) {
        return userRepository.findByPassword(password);
    }

    @Override
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public List<CustomUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void banUserById(Long id) {
        CustomUser uLoginUser = userRepository.findById(id);
        uLoginUser.setBanned(true);
    }

    @Transactional
    public void unbanUserById(Long id) {
        CustomUser uLoginUser = userRepository.findById(id);
        uLoginUser.setBanned(false);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.removeCustomUserById(id);
    }
}
