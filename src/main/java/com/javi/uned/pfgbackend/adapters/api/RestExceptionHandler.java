package com.javi.uned.pfgbackend.adapters.api;

import com.javi.uned.pfgbackend.domain.exceptions.AuthException;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.exceptions.ExistingUserException;
import com.javi.uned.pfgbackend.domain.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    protected ResponseEntity<Map<String, Object>> handleArithmeticException(RestException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> handleGenericException(Exception exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Unhandled exception: " + exception.getMessage());
        body.put("exception", exception);
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<RestExceptionBody> handleValidationException(Exception exception) {
        RestExceptionBody restExceptionBody = new RestExceptionBody();
        restExceptionBody.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restExceptionBody);
    }

    @ExceptionHandler(ExistingUserException.class)
    protected ResponseEntity<RestExceptionBody> handleExistingUserException(Exception exception) {
        RestExceptionBody restExceptionBody = new RestExceptionBody();
        restExceptionBody.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restExceptionBody);
    }

    @ExceptionHandler(EntityNotFound.class)
    protected ResponseEntity<RestExceptionBody> handleEntityNotFound(Exception exception) {
        RestExceptionBody restExceptionBody = new RestExceptionBody();
        restExceptionBody.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restExceptionBody);
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<RestExceptionBody> handleAuthException(AuthException exception) {
        RestExceptionBody restExceptionBody = new RestExceptionBody();
        restExceptionBody.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restExceptionBody);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<RestExceptionBody> handleAuthException(AccessDeniedException exception) {
        RestExceptionBody restExceptionBody = new RestExceptionBody();
        restExceptionBody.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restExceptionBody);
    }


}
