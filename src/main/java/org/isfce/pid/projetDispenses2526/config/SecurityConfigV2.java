package org.isfce.pid.projetDispenses2526.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;

/** V1 : HTTP Basic + 4 rôles simulés. V2 : remplacer par Keycloak/OIDC. */
//@Configuration
public class SecurityConfigV2 {

//    @Bean
//    public UserDetailsService users() {
//        UserDetails etu = User.withUsername("etudiant@isfce.be").password("{noop}pass").roles("ETUDIANT").build();
//        UserDetails dir = User.withUsername("direction@isfce.be").password("{noop}pass").roles("DIRECTION").build();
//        UserDetails prof = User.withUsername("prof@isfce.be").password("{noop}pass").roles("PROFESSEUR").build();
//        UserDetails sec = User.withUsername("secretariat@isfce.be").password("{noop}pass").roles("SECRETARIAT").build();
//        return new InMemoryUserDetailsManager(etu, dir, prof, sec);
//    }
//
//    @Bean
//    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable());
//        http.authorizeHttpRequests(auth -> auth
//            .requestMatchers("/h2-console/**").permitAll()
//            .requestMatchers("/api/**").authenticated()
//            .anyRequest().permitAll()
//        );
//        http.headers(h -> h.frameOptions(fr -> fr.disable())); // H2 console
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
}