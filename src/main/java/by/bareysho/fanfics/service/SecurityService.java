package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.CustomUser;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

    CustomUser getCurrentUser();
}
