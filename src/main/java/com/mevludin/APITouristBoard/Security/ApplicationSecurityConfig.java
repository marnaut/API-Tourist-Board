package com.mevludin.APITouristBoard.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.mevludin.APITouristBoard.Security.ApplicationUserRole.*;
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //for any visitor
                .antMatchers(HttpMethod.GET,"/api/v1/{municipalityId}/sights/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/{municipalityId}/sights/{id}").permitAll()
                //for editor and admin
                .antMatchers(HttpMethod.POST,"/api/v1/{municipalityId}/sights/**").hasAnyRole(EDITOR.name(),ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/api/v1/{municipalityId}/sights/**").hasAnyRole(EDITOR.name(),ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/api/v1/{municipalityId}/sights/{id}/delete/{photoId}").hasAnyRole(EDITOR.name(),ADMIN.name())
                //for admin
                .antMatchers("/api/v1/**").hasRole(ADMIN.name())


                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    //In memory users
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("test"))
                .roles(ADMIN.name())
                .build();

        UserDetails editor = User.builder()
                .username("editor")
                .password(passwordEncoder.encode("test"))
                .roles(EDITOR.name())
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                editor
        );

    }

}
