package org.isfce.pid.projetDispenses2526.config;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig {

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // Désactive CSRF pour faciliter les tests d'API (Postman)
//        http.csrf(csrf -> csrf.disable());
//
//        // Règles d'accès
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
//                .requestMatchers("/api/**").permitAll()   // API ouverte pour le moment
//                .anyRequest().permitAll()
//        );
//
//        // Autoriser H2 en <iframe>
//        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
//
//        return http.build();
//    }
}