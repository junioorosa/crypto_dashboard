package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosCadastroAporte(
        @NotBlank DadosCripto cripto,
        @NotBlank DadosCambio cambio,
        @NotBlank BigDecimal apoPrecoCripto,
        @NotBlank LocalDateTime apoData,
        @NotBlank BigDecimal apoValorAportado,
        @NotBlank BigDecimal apoTaxaCorretora,
        @NotBlank double apoQuantidadeCripto
) {
}
