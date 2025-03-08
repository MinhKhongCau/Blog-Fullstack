package com.myproject.blog.Security;

import com.myproject.blog.Service.AccountService;
import com.myproject.blog.Until.Constants.Json;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtProvider jwtProvider;

    public CustomUserNamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Username password filter loading...");

        String username = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);

        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        String token = jwtProvider.generateToken(userDetails);

        // Trả về token dưới dạng JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: " + failed.getMessage());

        response.setContentType(HttpHeaders.CONTENT_TYPE);
        response.setStatus(responseEntity.getStatusCode().value());
        response.getWriter().write(Json.toJson(responseEntity.getBody()));
    }

    @Component
    public static class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

        @Autowired
        private AccountService accountService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            System.out.println("***Load user authentication...");

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

            String principal = token.getPrincipal().toString();
            String credentials = token.getCredentials().toString();

            System.out.println("***Principal : " + principal);
            System.out.println("***Credential : " + credentials);
            UserDetails userDetail = accountService.loadUserByUsername(principal);

            if (!passwordEncoder.matches(credentials, userDetail.getPassword())) {
                System.out.println("***User name or password not match");
                throw new BadCredentialsException("Invalid username or password");
            }

            return new UsernamePasswordAuthenticationToken(userDetail, credentials, userDetail.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        }
    }
}
