package com.studentmanagement.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;

    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Auth routes
                        .requestMatchers("/api/auth/**").permitAll()

                        // Student routes
                        .requestMatchers("/api/students").hasRole("ADMIN")
                        .requestMatchers("/api/students/{studentId}").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/api/students/{studentId}/classes").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/api/students/{studentId}/enrollments").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/api/students/{studentId}/enroll/{classId}").hasRole("ADMIN")

                        // Teacher routes
                        .requestMatchers("/api/teachers").hasRole("ADMIN")
                        .requestMatchers("/api/teachers/{teacherId}").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/teachers/{teacherId}/classes").hasAnyRole("TEACHER", "ADMIN")

                        // Course routes
                        .requestMatchers("/api/courses").permitAll() 
                        .requestMatchers("/api/courses/**").hasRole("ADMIN") 

                        // Class routes
                        .requestMatchers("/api/classes").permitAll()  
                        .requestMatchers("/api/classes/**").hasRole("ADMIN")
                        .requestMatchers("/api/classes/{id}/**").hasRole("ADMIN")

                        // Department routes
                        .requestMatchers("/api/departments").permitAll()
                        .requestMatchers("/api/departments/**").hasRole("ADMIN")
                        .requestMatchers("/api/departments/{id}/**").hasRole("ADMIN")

                        // Academic year routes
                        .requestMatchers("/api/college-years").permitAll()  
                        .requestMatchers("/api/college-years/**").hasRole("ADMIN")

                        // College level routes
                        .requestMatchers("/api/college-levels").permitAll()  
                        .requestMatchers("/api/college-levels/**").hasRole("ADMIN")

                        // Term routes
                        .requestMatchers( "/api/terms").permitAll()
                        .requestMatchers("/api/terms/**").hasRole("ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
