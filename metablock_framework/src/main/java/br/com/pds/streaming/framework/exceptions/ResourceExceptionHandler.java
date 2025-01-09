package br.com.pds.streaming.framework.exceptions;

import br.com.pds.streaming.framework.exceptions.response.ResponseError;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidAnimationException.class)
    public ResponseEntity<ResponseError> invalidAnimation(InvalidAnimationException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for an animation", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidCreditCardNumberException.class)
    public ResponseEntity<ResponseError> invalidCreditCardNumber(InvalidCreditCardNumberException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseError(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid credit card number", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ResponseError> invalidRole(InvalidRoleException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid role", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidSourceException.class)
    public ResponseEntity<ResponseError> invalidSource(InvalidSourceException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid source", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidSubscriptionTypeException.class)
    public ResponseEntity<ResponseError> invalidSubscriptionType(InvalidSubscriptionTypeException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid subscription type", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidThumbnailException.class)
    public ResponseEntity<ResponseError> invalidThumbnail(InvalidThumbnailException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for a thumbnail", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidVideoException.class)
    public ResponseEntity<ResponseError> invalidVideo(InvalidVideoException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid file extension for a video", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MissingOrInvalidMediaException.class)
    public ResponseEntity<ResponseError> missingOrInvalidMedia(MissingOrInvalidMediaException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "A history node or a user has an invalid media or a list of invalid medias", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(TranscriptionFailedException.class)
    public ResponseEntity<ResponseError> transcriptionFailed(TranscriptionFailedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Transcription failed", e.getMessage(), request.getRequestURI()));
    }
}
