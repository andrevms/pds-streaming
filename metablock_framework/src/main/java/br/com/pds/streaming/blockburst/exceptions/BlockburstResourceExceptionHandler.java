package br.com.pds.streaming.blockburst.exceptions;

import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.framework.exceptions.response.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class BlockburstResourceExceptionHandler {

    @ExceptionHandler(InvalidVideoException.class)
    public ResponseEntity<ResponseError> invalidVideo(InvalidVideoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for a video", e.getMessage(), request.getRequestURI()));
    }
}
