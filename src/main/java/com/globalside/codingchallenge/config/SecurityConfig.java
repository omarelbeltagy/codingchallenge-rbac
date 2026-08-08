package com.globalside.codingchallenge.rbac.config;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.GET, "/products").hasRole("USER")
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails adminUserDetails = User.withUsername("admin")
			.password(passwordEncoder.encode("password"))
			.roles("ADMIN")
			.build();
            
		UserDetails userDetails = User.withUsername("user")
			.password(passwordEncoder.encode("password"))
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(adminUserDetails, userDetails);
	}

}