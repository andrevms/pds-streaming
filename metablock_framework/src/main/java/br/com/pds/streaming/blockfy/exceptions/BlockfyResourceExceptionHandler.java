package br.com.pds.streaming.blockfy.exceptions;

import br.com.pds.streaming.framework.exceptions.response.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class BlockfyResourceExceptionHandler {

    @ExceptionHandler(InvalidAudioException.class)
    public ResponseEntity<ResponseError> invalidAudio(InvalidAudioException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for an audio", e.getMessage(), request.getRequestURI()));
    }
}
