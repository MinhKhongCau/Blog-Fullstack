package com.myproject.blog.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private static final String[] WHILELIST = {
        "/",
        "/home",
        "/register",
        "/db-console/**",
        "/login/**",
        "/forgot-password/**",
        "/change-password/**",
        "/about/**",
        "/resources/**",
        "/demo/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers(WHILELIST).permitAll()
                // .requestMatchers("/post/**").authenticated()
                // .requestMatchers("/admin/**").hasRole(Roles.ADMIN.getRole())
                // .requestMatchers("/editor/**").hasAnyRole(Roles.ADMIN.getRole(),Roles.EDITOR.getRole())
                // .requestMatchers("/admin/**").hasAuthority(Privillage.ACCESS_ADMIN_PANEL.getName())
                // .requestMatchers("/profile/**").authenticated()
            );
            // Config when no permission page will be redirect login page '/login'
        http.csrf(csrf -> csrf .disable());
        http.headers(h -> h.frameOptions(c -> c.disable()));

        return http.build();
    }

    @Bean
    static PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
