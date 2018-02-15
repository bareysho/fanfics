package by.bareysho.fanfics.mail;

import by.bareysho.fanfics.service.EmailTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private EmailTokenService emailTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String token = emailTokenService.generateToken(event.getUser());
        URL confirmationUrl = null;
        try {
            confirmationUrl = new URL("http://localhost:8080" + event.getAppUrl() + "/confSuccess?token=" + token);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        MimeMessage email = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(email, true);
            helper.setTo(event.getUser().getEmail());
            helper.setSubject("Registration Confirmation");
            helper.setFrom("redkovskiyandrey@gmail.com");
            helper.setText("<html><body><p>Congratulations. To confirm registration follow <a href=\"" +
                    confirmationUrl + "\">this link</a></p></body></html>", true);
            mailSender.send(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
