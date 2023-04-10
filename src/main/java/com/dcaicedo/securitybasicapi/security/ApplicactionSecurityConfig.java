package com.dcaicedo.securitybasicapi.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.dcaicedo.securitybasicapi.security.ApplicationUserRole.*;
import static com.dcaicedo.securitybasicapi.security.ApplicationUserPermission.*;

/**
 * <p>
 * Esta clase permite configurar la seguridad de la aplicacion
 * </p>
 *
 * @author Daniel Caicedo
 * @version 1.0, 06/04/2023
 */

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ApplicactionSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    /**
     * <p>
     * Configura las seguridad de las peticiones que se realicen
     * </p>
     * <ul>
     *     <li>Dehabilita crf</li>
     *     <li>Permite peticiones sin autenticacion a los endpoints de autenticacion</li>
     *     <li>Las peticiones al resto de API estan permitidas bajo unicamente el rol USER</li>
     *     <li>El tipo de autenticacion es Basic</li>
     * </ul>
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/auth/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/**/demo/user").hasAnyRole(USER.name(), ADMIN.name())
               // .antMatchers(HttpMethod.GET, "/api/**/demo/admin").hasAnyAuthority(ADMIN_GET.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * <p>
     * Bean Permite almacenar usuarios preconfigurados en memoria y da acceso a ellos ya que se encuentra en el
     * contenedor de Beans
     * </p>
     *
     * @return intancia de una de las implementaciones de UserDetailService la cual es el almacenamiento de los
     * usuarios en memoria InMemoryUserDetailsManager que contiene los usuarios creados en el metodo
     */
    @Bean
    @Override protected UserDetailsService userDetailsService() {
        log.info("password userDetails1 is encode like {}", passwordEncoder.encode("password"));
        UserDetails userDetails1 =
                User.builder()
                        .username("daniel")
                        .password(passwordEncoder.encode("password"))
                        .roles(USER.name()) // ROLE_USER
                        .build();
        UserDetails userDetails2 =
                User.builder().
                        username("elias")
                        .password(passwordEncoder.encode("password"))
                        .roles(ADMIN.name())
                        .authorities(ADMIN.getAuthorities())
                        .build();
        UserDetails userDetails3 =
                User.builder()
                        .username("leo")
                        .password(passwordEncoder.encode("password"))
                        .roles(USER.name())
                        .build();
        return new InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3);
    }
}
