package by.bareysho.fanfics.security.ulogin;

import by.bareysho.fanfics.model.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import java.net.URL;
import java.net.URLConnection;


public class UloginAuthentificationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(UloginAuthentificationProvider.class);

    private final String host;

    public UloginAuthentificationProvider(String host) {
        this.host = host;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        ULoginAuthToken uLoginAuthToken = (ULoginAuthToken) authentication;

        try {
            URL uloginUrl = new URL("http://ulogin.ru/token.php?token=" + uLoginAuthToken.getCredentials() + "&host=" + host);
            URLConnection urlConnection = uloginUrl.openConnection();

            JsonReader jsonReader = Json.createReader(urlConnection.getInputStream());
            JsonObject obj = jsonReader.readObject();

            if (obj == null) {
                throw new BadCredentialsException("ulogin did't return json object");
            }
            if (obj.getJsonString("identity") == null) {
                throw new BadCredentialsException("ulogin did't return identity object");
            }

            String identity = obj.getJsonString("identity").getString();
            LOG.info(identity);

            CustomUser uLoginUser = new CustomUser();

            uLoginUser.setUsername(getStringProp(obj, "uid"));
            uLoginUser.setPassword(getStringProp(obj, "identity"));
            uLoginUser.setEnabled(true);

            uLoginAuthToken.setuLoginUser(uLoginUser);
            uLoginAuthToken.setAuthenticated(true);


        } catch (Exception ex) {
            uLoginAuthToken.setAuthenticated(false);
            LOG.error(ex.getMessage(), ex);
            throw new AuthenticationServiceException(ex.getMessage());
        }
        return uLoginAuthToken;

    }

    private String getStringProp(JsonObject obj, String prop) {
        JsonString jsonString = obj.getJsonString(prop);
        if (jsonString == null) {
            return null;
        }
        return jsonString.getString();
    }


    public boolean supports(Class<?> authentication) {
        return ULoginAuthToken.class.isAssignableFrom(authentication);
    }
}
