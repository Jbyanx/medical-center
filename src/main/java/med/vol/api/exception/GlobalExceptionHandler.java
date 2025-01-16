package med.vol.api.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)//404
    public ResponseEntity<Object> objectNotFound(ObjectNotFoundException e) {
        return new ResponseEntity<>("La entidad solicitada no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> objectNotFound(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Error en la integridad de los datos, verifique los constraints", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)//400
    public ResponseEntity<Object> objectNotFound(MethodArgumentNotValidException e) {
        var errores = e.getAllErrors();

        return new ResponseEntity<>("los parametros indicados son erroneos o tienen falencias", HttpStatus.NOT_FOUND);
    }

}
