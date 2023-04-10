package com.dcaicedo.securitybasicapi.demo;

import com.dcaicedo.securitybasicapi.shared.http.GenericHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * <p>
 *     Controlador de prueba para validar el funcionamiento por roles
 * </p>
 * @author Daniel Caicedo
 * @version 1.0.0, 07/04/2023
 */
@RestController
@RequestMapping("api/v1/demo")
public class DemoController {

    /**
     * <p>
     *     Metodo de prueba para validar el funcionamiento del rol USER
     * </p>
     * @return Respuesta generica con estado OK en caso de que la peticion fuera realizada por un usuario con rol USER
     */
    @GetMapping(
            value = "/user"
    )
    public ResponseEntity<GenericHttpResponse> getOnlyUserRole(){
        return ResponseEntity.ok(
                GenericHttpResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Collections.singletonMap("data", "ok"))
                        .message("Is a user role")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    /**
     * <p>
     *     Metodo de prueba para validar el funcionamiento del rol ADMIN
     * </p>
     * @return Respuesta generica con estado OK en caso de que la peticion fuera realizada por un usuario con rol ADMIN
     */
    @PostAuthorize("hasAnyAuthority('admin:insert','admin:get')")
    @GetMapping(
            value = "/admin"
    )
    public ResponseEntity<GenericHttpResponse> getOnlyAdminRole(){
        return ResponseEntity.ok(
                GenericHttpResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Collections.singletonMap("data", "ok"))
                        .message("Is a admin role")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


}
