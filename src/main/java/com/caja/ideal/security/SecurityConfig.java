package com.caja.ideal.security;

import com.caja.ideal.jwt.JwtAuthenticationFilter;
import com.caja.ideal.logout.LogoutService;
import com.caja.ideal.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private LogoutService logoutHandler;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AppConstants.NO_SECURED_URLs)
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AppConstants.SECURED_URLs)
                .authenticated()
                .and()
                .httpBasic().and().formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/logout/**")
                .addLogoutHandler(logoutHandler)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
