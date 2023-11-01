package br.com.crypto_dashboard.infra.dto;

import org.springframework.validation.FieldError;

public record ErroValidacaoDto(
        String campo,
        String mensagem
) {
    public ErroValidacaoDto(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
