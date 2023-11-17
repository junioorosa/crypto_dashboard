package br.com.crypto_dashboard.infra.exception;

import br.com.crypto_dashboard.infra.dto.ErroValidacaoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.logging.Logger;

@RestControllerAdvice
public class ExceptionHandling {

    Logger logger = Logger.getLogger(ExceptionHandling.class.getName());

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarErro404(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Object> tratarErro404(ResourceAccessException ex) {
        logger.info(ex.getMessage() + ex.getCause());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ErroValidacaoDto::new).toList());
    }

    @ExceptionHandler(UserDoesNotOwnResourceException.class)
    public ResponseEntity<Object> tratarErro404(UserDoesNotOwnResourceException ex) {
        return ResponseEntity.notFound().build();
    }

}
