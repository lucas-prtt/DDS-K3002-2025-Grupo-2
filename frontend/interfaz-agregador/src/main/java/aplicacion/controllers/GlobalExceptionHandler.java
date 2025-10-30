package aplicacion.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientRequestException.class)
    public String handleWebClientException(WebClientRequestException ex) {
        return "error/error-server-offline";
    }

    @ExceptionHandler(WebClientResponseException.class)
    public String handleWebClientException(WebClientResponseException ex) {
        if(ex.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) // 504, no se pudo conectar a agregador
            return "error/error-server-offline";
        else
            return "error/500.html";
    }
}