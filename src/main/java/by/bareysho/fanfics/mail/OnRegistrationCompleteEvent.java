//package by.bareysho.fanfics.mail;
//
//import by.bareysho.fanfics.model.CustomUser;
//import org.springframework.context.ApplicationEvent;
//
//import java.util.Locale;
//
//public class OnRegistrationCompleteEvent extends ApplicationEvent {
//    private String appUrl;
//    private Locale locale;
//    private CustomUser user;
//
//    public OnRegistrationCompleteEvent(
//            CustomUser user, Locale locale, String appUrl) {
//        super(user);
//
//        this.user = user;
//        this.locale = locale;
//        this.appUrl = appUrl;
//    }
//
//    public String getAppUrl() {
//        return appUrl;
//    }
//
//    public void setAppUrl(String appUrl) {
//        this.appUrl = appUrl;
//    }
//
//    public Locale getLocale() {
//        return locale;
//    }
//
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }
//
//    public CustomUser getUser() {
//        return user;
//    }
//
//    public void setUser(CustomUser user) {
//        this.user = user;
//    }
//}
