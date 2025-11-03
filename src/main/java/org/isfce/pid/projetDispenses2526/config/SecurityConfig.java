package org.isfce.pid.projetDispenses2526.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Autoriser la console H2
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().permitAll() // (pour tes tests, tout est ouvert)
        );

        // Désactiver CSRF uniquement pour H2
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));

        // Autoriser l’affichage en <iframe> (H2 utilise des frames)
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
        // NOTE: si PathRequest n'est pas dispo, remplace-le par "/h2-console/**" partout.
        // .requestMatchers("/h2-console/**")
    }
}
