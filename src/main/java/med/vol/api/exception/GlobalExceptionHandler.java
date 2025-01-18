package med.vol.api.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class) // 404
    public ResponseEntity<Object> handleObjectNotFound(ObjectNotFoundException e) {
        return new ResponseEntity<>("El objeto solicitado no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class) // 409
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Error en la integridad de los datos, verifique los constraints", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 400
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class) // 400
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        String message = String.format("El parámetro '%s' es obligatorio", e.getParameterName());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) // 405
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String message = String.format("El método '%s' no está soportado para esta petición. Métodos soportados: %s",
                e.getMethod(), e.getSupportedHttpMethods());
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class) // 403
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException e) {
        return new ResponseEntity<>("Acceso denegado. No tienes permisos para acceder a este recurso.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class) // 500
    public ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>("Ha ocurrido un error interno en el servidor. Por favor, contacta al administrador.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
