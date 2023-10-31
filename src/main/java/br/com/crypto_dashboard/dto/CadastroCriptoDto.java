package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotNull;

public record CadastroCriptoDto(
        @NotNull String criIdApi
) {
}