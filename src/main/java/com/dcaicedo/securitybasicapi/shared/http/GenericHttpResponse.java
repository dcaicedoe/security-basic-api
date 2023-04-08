package com.dcaicedo.securitybasicapi.shared.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     Clases de respuesta http generica para las peticiones realizadas. Esta clase servira tanto para las peticiones
 *     con errores como para las que contiene errores
 * </p>
 * @author Daniel Caicedo
 * @since 1.0.0, 07/04/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericHttpResponse {

    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String developerMessage;
    private List<String> errors;
    private String stackTrace;
    private Map<?,?> data;

    public List<String> getErrors() {
        if(errors == null){
            errors = new ArrayList<>();
        }
        return errors;
    }
}
