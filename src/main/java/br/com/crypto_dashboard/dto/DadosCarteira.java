package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCarteira(
        @NotBlank String carDescricao
) {
}
