package uz.etc.etcfitness.handler;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.etc.etcfitness.exception.ActivationTokenException;
import uz.etc.etcfitness.exception.ItemNotFoundException;
import uz.etc.etcfitness.exception.MessagingException;
import uz.etc.etcfitness.exception.OperationNotPermittedException;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;
import static uz.etc.etcfitness.handler.BusinessErrorCodes.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ACCOUNT_LOCKED.getCode())
                                .errorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ACCOUNT_DISABLED.getCode())
                                .errorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(BAD_CREDENTIALS.getCode())
                                .errorDescription(BAD_CREDENTIALS.getDescription())
                                .error("Login and / or Password is incorrect")
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(INTERNAL_SERVER_ERROR.value())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(ActivationTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(BAD_REQUEST.value())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(BAD_REQUEST.value())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ItemNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(NOT_FOUND.value())
                                .errorDescription(NOT_FOUND.getReasonPhrase())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(BAD_REQUEST.value())
                                .errorDescription(BAD_REQUEST.getReasonPhrase())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccessDeniedException exp) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .errorCode(FORBIDDEN.value())
                                .errorDescription(FORBIDDEN.getReasonPhrase())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .errorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .errorCode(INTERNAL_SERVER_ERROR.value())
                                .timestamp(System.currentTimeMillis())
                                .build()
                );
    }
}
