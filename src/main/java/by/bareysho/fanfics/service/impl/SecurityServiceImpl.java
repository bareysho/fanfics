package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.service.SecurityService;
import by.bareysho.fanfics.service.UserService;
import by.bareysho.fanfics.model.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            logger.debug(String.format("Successfully %s auto logged in", username));
    }

    public CustomUser getCurrentUser(){
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if(userObject instanceof User){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = user.getUsername();
        }
        if(userObject instanceof CustomUser){
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = user.getUsername();
        }

        CustomUser dbCustomUser = userService.findByUsername(username);

        return dbCustomUser;
    }
}
