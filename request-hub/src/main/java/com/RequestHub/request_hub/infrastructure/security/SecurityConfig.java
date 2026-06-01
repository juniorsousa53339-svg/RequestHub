package com.RequestHub.request_hub.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain
    springSecurityFilterChain(org.springframework.security.config.annotation.web.builders.
                                      HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // liberar console H2 no dev
                        .requestMatchers("/h2-console/**").permitAll()

                        // ADMIN
                        .requestMatchers(HttpMethod.GET, "/solicitacoes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/solicitacoes/*/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/solicitacoes/*").hasRole("ADMIN")

                        // SOLICITANTE
                        .requestMatchers(HttpMethod.POST, "/solicitacoes").hasRole("SOLICITANTE")
                        .requestMatchers(HttpMethod.GET, "/solicitacoes/minhas").hasRole("SOLICITANTE")
                        .requestMatchers(HttpMethod.PUT, "/solicitacoes/*/cancelar").hasRole("SOLICITANTE")
                        .requestMatchers(HttpMethod.PUT, "/solicitacoes/*").hasRole("SOLICITANTE")

                        // qualquer outra rota precisa estar logado
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        // necessário pro H2 console funcionar (iframe)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails solicitante = User.withUsername("luciano")
                .password(encoder.encode("123"))
                .roles("SOLICITANTE")
                .build();

        return new InMemoryUserDetailsManager(admin, solicitante);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



