package by.bareysho.fanfics.security.ulogin;

import by.bareysho.fanfics.model.CustomUser;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UloginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UloginAuthenticationFilter(String url) {
        super(new AntPathRequestMatcher(url, "POST"));

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String token = request.getParameterValues("token")[0];
        ULoginAuthToken authRequest = new ULoginAuthToken(token);
        Authentication auth = this.getAuthenticationManager().authenticate(authRequest);

        CustomUser uLoginUser = (CustomUser) auth.getPrincipal();
        request.getSession().setAttribute("username", uLoginUser.getUsername());

        return auth;
    }
}