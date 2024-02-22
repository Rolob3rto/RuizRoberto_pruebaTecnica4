package com.roberto.PruebaTecnica4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/flights/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/hotels/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/persons/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/persons/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/hotel-booking/new").permitAll()
                .requestMatchers(HttpMethod.POST, "/flight-booking/new").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }

}
