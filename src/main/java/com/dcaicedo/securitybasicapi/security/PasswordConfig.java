package com.dcaicedo.securitybasicapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Daniel Caicedo
 * @version 1.0, 07/04/2023
 */
@Configuration
public class PasswordConfig {

    /**
     * <p>
     *     Bean que permite configurar la encriptacion de la constrseña
     * </p>
     * @return instancia de una implementacion de PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
