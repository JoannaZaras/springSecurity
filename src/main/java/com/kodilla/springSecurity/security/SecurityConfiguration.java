package com.kodilla.springSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/messages/**")
                .hasAnyRole("R1", "R2", "R3")
                .mvcMatchers(HttpMethod.POST, "/**")
                .hasAnyRole("R1", "R2")
                .mvcMatchers(HttpMethod.DELETE, "/**")
                .hasAnyRole("R1")
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("r1").password("r1").roles("R1");
        auth.inMemoryAuthentication().withUser("r2").password("r2").roles("R2");
        auth.inMemoryAuthentication().withUser("r3").password("r3").roles("R3");
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (org.springframework.security.crypto.password.NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}