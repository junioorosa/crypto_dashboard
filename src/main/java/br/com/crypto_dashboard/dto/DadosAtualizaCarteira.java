package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizaCarteira(
        @NotNull Long id,
        @NotBlank String carDescricao
) {
}
