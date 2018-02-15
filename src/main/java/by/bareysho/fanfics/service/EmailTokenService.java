package by.bareysho.fanfics.service;

import by.bareysho.fanfics.model.CustomUser;

public interface EmailTokenService {
    String generateToken(CustomUser user);

    boolean validateToken(String token, CustomUser user);
}
