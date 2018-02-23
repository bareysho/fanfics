package by.bareysho.fanfics.security.ulogin;

import by.bareysho.fanfics.model.CustomUser;
import by.bareysho.fanfics.model.Role;
import by.bareysho.fanfics.repository.RoleRepository;
import by.bareysho.fanfics.repository.UserRepository;
import by.bareysho.fanfics.service.RoleService;
import by.bareysho.fanfics.service.UserService;
import by.bareysho.fanfics.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@Component
public class UloginAuthenticationFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UloginAuthentificationProvider uloginAuthProvider;

    public CustomUser attemptAuthentication(WebRequest request) {

        String token = request.getParameterValues("token")[0];
        ULoginAuthToken authRequest = new ULoginAuthToken(token);

        CustomUser loggedUser;
        CustomUser customUser = (CustomUser) uloginAuthProvider.authenticate(authRequest).getPrincipal();
        CustomUser dbUser = userService.findByUsername(customUser.getUsername());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (dbUser == null){
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            grantedAuthorities.add(new SimpleGrantedAuthority("SOC_USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName("USER"));
            roles.add(roleService.findByName("SOC_USER"));
            customUser.setRoles(roles);

            userService.save(customUser);
            loggedUser = customUser;

        } else {

            for (Role role : dbUser.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            loggedUser = dbUser;
        }

        Authentication authentication = uloginAuthProvider.authenticate(new ULoginAuthToken(token, grantedAuthorities));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return loggedUser;
    }
}