package by.bareysho.fanfics;

import by.bareysho.fanfics.security.ulogin.UloginAuthenticationFilter;
import by.bareysho.fanfics.security.ulogin.UloginAuthentificationProvider;
import by.bareysho.fanfics.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UloginAuthenticationFilter uloginFilter = new UloginAuthenticationFilter("/ulogin");
        uloginFilter.setAuthenticationManager(authenticationManager());

        http
                .addFilterBefore(uloginFilter, AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*").permitAll()
                .antMatchers("/", "/welcome", "/admin").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and().csrf().disable();

    }


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()).and()
                .authenticationProvider(new UloginAuthentificationProvider("localhost:8080"));
    }
}