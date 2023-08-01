package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotNull;

//TODO ainda não sei como fazer o upload de imagens
public record DadosCadastroCripto(
        @NotNull String criIdApi,
        @NotNull String criDescricao,
        @NotNull String criImagem
) {
}