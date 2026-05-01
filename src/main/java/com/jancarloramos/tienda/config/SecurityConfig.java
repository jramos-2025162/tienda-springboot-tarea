package com.jancarloramos.tienda.config;

import com.jancarloramos.tienda.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(AuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Acceso permitido para todos sin iniciar sesión
                        .requestMatchers("/login", "/register", "/access-denied", "/css/**", "/js/**", "/images/**").permitAll()

                        //  Solo usuarios con ROLE_ADMIN pueden gestionar usuarios, detalles y categorías
                        // He añadido "/categoria/**" para que el administrador sea el único que las gestione
                        .requestMatchers("/usuario/**", "/detalles/**", "/categoria/**").hasRole("ADMIN")

                        // Acceso a productos y pedidos
                        // El ROLE_USER puede ver productos pero no gestionar la configuración del sistema
                        .requestMatchers("/productos/**", "/pedidos/**").hasAnyRole("ADMIN", "USER")

                        //  Requiere que el usuario esté al menos autenticado
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true) // Cambiado a productos para ir directo a la tienda
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // Redirección si un USER intenta entrar a una ruta de ADMIN
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                )
                .csrf(csrf -> csrf.disable()); // Deshabilitado para facilitar las pruebas locales

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(authService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }
}