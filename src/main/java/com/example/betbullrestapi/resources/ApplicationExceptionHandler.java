package com.example.betbullrestapi.resources;

import com.example.betbullrestapi.dto.ApiMessage;
import com.example.betbullrestapi.exceptions.DomainNotFoundException;
import com.example.betbullrestapi.exceptions.TransferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleConflict(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }

    @ExceptionHandler(DomainNotFoundException.class)
    protected ResponseEntity<?> handleConflict(DomainNotFoundException ex, WebRequest request){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setMessage(ex.getMessage());
        apiMessage.setDate(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiMessage);
    }

    @ExceptionHandler(TransferException.class)
    protected ResponseEntity<?> handleConflict(TransferException ex, WebRequest request){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setMessage(ex.getMessage());
        apiMessage.setDate(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiMessage);
    }
}
