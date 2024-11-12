package br.com.pds.streaming.exceptions;

import br.com.pds.streaming.exceptions.response.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DuplicatedRatingException.class)
    public ResponseEntity<ResponseError> duplicatedRating(DuplicatedRatingException e, HttpServletRequest request) {
        ResponseError err = new ResponseError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Duplicated rating", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        ResponseError err = new ResponseError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
