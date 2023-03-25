package com.scm.crud.controllers.handles;

import com.scm.crud.dto.CustomError;
import com.scm.crud.dto.ValidationError;
import com.scm.crud.services.exceptions.DatabaseException;
import com.scm.crud.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

public class ControllerExceptionHandler {
    @ControllerAdvice
    public class controllerExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
            // HttpServletRequest request-> recebendo a url que deu exceção
            HttpStatus status = HttpStatus.NOT_FOUND; // 401
            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
            return ResponseEntity.status(status).body(err);
        }
        @ExceptionHandler(DatabaseException.class)
        public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
            // HttpServletRequest request-> recebendo a url que deu exceção
            HttpStatus status = HttpStatus.BAD_REQUEST; // 400
            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
            return ResponseEntity.status(status).body(err);
        }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
            // HttpServletRequest request-> recebendo a url que deu exceção
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // 422
            ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
            for (FieldError f: e.getBindingResult().getFieldErrors()){//  percorre todos os erros que estiverem em "e." chamando eles de "f"
                err.addError(f.getField(), f.getDefaultMessage()); // para cada um "f" vou adicionar o comando de erro
            }
            return ResponseEntity.status(status).body(err);
        }
    }
}


