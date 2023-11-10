package br.com.crypto_dashboard.infra.exception;

import br.com.crypto_dashboard.infra.dto.ErroValidacaoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarErro404() {
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
