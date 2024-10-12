package com.GreenThumb.Config;

import com.GreenThumb.Config.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/rendezvous/**").hasAnyAuthority("ROLE_Client", "ROLE_ADMIN","ROLE_Jardinier")
                        .requestMatchers("/taches/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/taches").permitAll()
                        .requestMatchers("/equipements/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/equipements").permitAll()
                        .requestMatchers("/auth/users**").hasAuthority("ROLE_ADMIN")


                        .anyRequest().permitAll() // All other requests require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before the default username/password filter

        return http.build();
    }
}
