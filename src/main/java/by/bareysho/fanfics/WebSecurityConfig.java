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

        http
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/images/*").permitAll()
                .antMatchers("/","/welcome","/register", "/login", "/ulogin", "/readFanfic/**").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/admin").access("hasAuthority('ADMIN')")
                .and()
                .authorizeRequests().and().exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and().csrf().disable();
    }


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UloginAuthentificationProvider uloginAuthentificationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()).and()
                .authenticationProvider(uloginAuthentificationProvider);
    }
}