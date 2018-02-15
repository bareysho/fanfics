package by.bareysho.fanfics.service.impl;

import by.bareysho.fanfics.service.EmailTokenService;
import by.bareysho.fanfics.model.CustomUser;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Service
public class EmailTokenServiceImpl implements EmailTokenService {

    public String generateToken(CustomUser user) {
        Date date = new Date();
        DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        byte[] time = writeFormat.format(date).getBytes();
        byte[] username = user.getUsername().toString().getBytes();

        byte[] data = new byte[time.length + username.length];

        System.arraycopy(time, 0, data, 0, time.length);
        System.arraycopy(username, 0, data, time.length, username.length);

        return new String(Base64.getEncoder().encode(data));
    }

    public boolean validateToken(String token, CustomUser user) {
        byte[] data = Base64.getDecoder().decode(token.getBytes());
        byte[] time = new byte[data.length - user.getUsername().getBytes().length];
        byte[] username = new byte[data.length - time.length];

        System.arraycopy(data, 0, time, 0, time.length);
        System.arraycopy(data, time.length, username, 0, username.length);

        String timeStr = new String(time, StandardCharsets.UTF_8);
        String usernameStr = new String(username, StandardCharsets.UTF_8);

        Date curDate = new Date();
        int differenceInHours;
        try{
            DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            Date tokenDate = readFormat.parse(timeStr);
            long difference = curDate.getTime() - tokenDate.getTime();
            differenceInHours = (int)difference/(60*60*1000);
        } catch (ParseException e){
            return false;
        }

        if (differenceInHours < 24 || user.getUsername().compareTo(usernameStr) == 0){
            return true;
        }

        return false;
    }
}
