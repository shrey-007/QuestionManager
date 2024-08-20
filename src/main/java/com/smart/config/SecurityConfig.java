package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Autowired
    CustomUserDetailService customUserDetailService;

    // spring security UserDetailsService ko use krta hai jabhi bhi apan login krte hai, us service ke paas ek method hota hai
    // loadUserByUserName us method ko use krega, user ko load krane ke liye. Fir loaded user and humara user ka password match
    // krega , if it matches toh login kra dega


    // If we want ki spring user ki details databse se le toh hum ye krege
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        httpSecurity.formLogin(formLogin->{
           formLogin.loginPage("/login");
           formLogin.loginProcessingUrl("/do_login");
           formLogin.successForwardUrl("/user/dashboard");

           formLogin.usernameParameter("email");
           formLogin.passwordParameter("password");
        });

        httpSecurity.logout(logoutForm->{
           logoutForm.logoutUrl("/logout");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
