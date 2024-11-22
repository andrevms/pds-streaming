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
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Duplicated rating", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidVideoException.class)
    public ResponseEntity<ResponseError> invalidVideo(InvalidVideoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for a video", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidThumbnailException.class)
    public ResponseEntity<ResponseError> invalidThumbnail(InvalidThumbnailException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for a thumbnail", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidAnimationException.class)
    public ResponseEntity<ResponseError> invalidAnimation(InvalidAnimationException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for an animation", e.getMessage(), request.getRequestURI()));
    }
}
