package by.bareysho.fanfics.security.ulogin;

import by.bareysho.fanfics.model.CustomUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

class ULoginAuthToken extends AbstractAuthenticationToken {
    private final String token;
    private CustomUser uLoginUser;

    public ULoginAuthToken(String token) {
        super(null);
        this.token = token;
    }

    public CustomUser getuLoginUser() {
        return uLoginUser;
    }

    public void setuLoginUser(CustomUser uLoginUser) {
        this.uLoginUser = uLoginUser;
    }

    public Object getCredentials() {
        return token;
    }


    public Object getPrincipal() {
        return uLoginUser;
    }

    @Override
    public String getName() {
        return uLoginUser.getUsername();
    }
}

