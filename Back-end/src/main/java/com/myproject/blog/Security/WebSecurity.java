package com.myproject.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Autowired
	private CustomUserNamePasswordAuthenticationFilter.UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

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
			"/demo/**" };

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

		http.authorizeHttpRequests(request -> request.requestMatchers(WHILELIST).permitAll()
				.requestMatchers("/post/**").authenticated()
				// .requestMatchers("/admin/**").hasRole(Roles.ADMIN.getRole())
				// .requestMatchers("/admin/**").hasAuthority(Privillage.ACCESS_ADMIN_PANEL.getName())
				.requestMatchers("/profile/**").authenticated())
				// Config when no permission page will be redirect login page '/login'
				.securityContext(context -> context.requireExplicitSave(false))
				.authenticationProvider(usernamePasswordAuthenticationProvider)
				.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/home").permitAll())
				.csrf(scrt -> scrt.disable());
		// Add Filter for security
		http
				.addFilterBefore(customFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public static PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(usernamePasswordAuthenticationProvider)
				.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() throws Exception {
		return new CustomUserNamePasswordAuthenticationFilter.UsernamePasswordAuthenticationProvider();
	}

	@Bean
	public CustomUserNamePasswordAuthenticationFilter customFilter(AuthenticationManager authenticationManager) {
		return new CustomUserNamePasswordAuthenticationFilter(authenticationManager);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter();
	}
}
