package br.com.bmont.sorteador.dtos.handler;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title("Bad Request Exception, Check the Documentation")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(badRequestException.getMessage())
                        .developerMessage(badRequestException.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }

}
