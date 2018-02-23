package by.bareysho.fanfics;

import by.bareysho.fanfics.security.ulogin.UloginAuthentificationProvider;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableAsync
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UloginAuthentificationProvider uloginAuthentificationProvider() {return new UloginAuthentificationProvider("localhost:8080");}

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("256KB");
        factory.setMaxRequestSize("256KB");
        return factory.createMultipartConfig();
    }

    @Value("${cloudinary.cloud_name}")
    private  String cloudinaryCloudName;

    @Value("${cloudinary.api_key}")
    private String cloudinaryApiKey;

    @Value("${cloudinary.api_secret}")
    private String cloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return  new Cloudinary(Cloudinary.asMap(
                "cloud_name", cloudinaryCloudName,
                "api_key", cloudinaryApiKey,
                "api_secret", cloudinaryApiSecret));

    }
}
