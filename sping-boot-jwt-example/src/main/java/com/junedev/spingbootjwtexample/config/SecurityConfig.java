package com.junedev.spingbootjwtexample.config;

import com.junedev.spingbootjwtexample.jwt.JwtAccessDeniedHandler;
import com.junedev.spingbootjwtexample.jwt.JwtAuthEntryPoint;
import com.junedev.spingbootjwtexample.jwt.JwtSecurityConfig;
import com.junedev.spingbootjwtexample.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(this.jwtAuthEntryPoint))
                .exceptionHandling(e -> e.accessDeniedHandler(this.jwtAccessDeniedHandler))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/api/**").permitAll().anyRequest().authenticated())
                .apply(new JwtSecurityConfig(this.jwtTokenProvider));
                return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
