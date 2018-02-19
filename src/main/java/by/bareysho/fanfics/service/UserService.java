package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.CustomUser;

import java.util.List;

public interface UserService {

    void save(CustomUser user);

    CustomUser findByUsername(String username);

    CustomUser findByEmail(String email);

    CustomUser findByPassword(String password);

    String encodePassword(String password);

    void banUserById(Long id);

    void unbanUserById(Long id);

    void deleteUserById(Long id);

    List<CustomUser> findAll();

    CustomUser getLoginUser();

    CustomUser findByConfirmationToken(String token);

}
