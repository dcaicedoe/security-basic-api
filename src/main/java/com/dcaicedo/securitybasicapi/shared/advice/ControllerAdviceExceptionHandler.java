package com.dcaicedo.securitybasicapi.shared.advice;

import com.dcaicedo.securitybasicapi.shared.http.GenericHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        GenericHttpResponse genericHttpResponse =
                GenericHttpResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message(ex.getMessage())
                        .status(status)
                        .statusCode(status.value())
                        .developerMessage(ex.getMessage())
                        .build();

        Objects.requireNonNull(ex.getSupportedHttpMethods())
                .stream()
                .map(Enum::name)
                .forEach(genericHttpResponse.getErrors()::add);
        return new ResponseEntity<Object>(genericHttpResponse, genericHttpResponse.getStatus());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<GenericHttpResponse> handleException(RuntimeException ex) {

        //Obtiene la pila de seguimiento
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        GenericHttpResponse genericHttpResponse =
                GenericHttpResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(ex.getMessage())
                        .stackTrace(stackTrace)
                        .build();
        return new ResponseEntity<>(genericHttpResponse, genericHttpResponse.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericHttpResponse> handleAllException(Exception exc) {
        GenericHttpResponse genericHttpResponse =
                GenericHttpResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message(exc.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .developerMessage(exc.getMessage())
                        .build();
        return new ResponseEntity<>(genericHttpResponse, genericHttpResponse.getStatus());
    }


}
