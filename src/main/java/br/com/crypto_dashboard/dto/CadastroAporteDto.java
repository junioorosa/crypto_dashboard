package br.com.crypto_dashboard.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastroAporteDto(
        @NotNull Long criptoId,
        @NotNull Long cambioId,
        @NotNull Long carteiraId,
        @NotNull BigDecimal apoPrecoCripto,
        @NotNull LocalDateTime apoData,
        @NotNull BigDecimal apoValorAportado,
        @NotNull BigDecimal apoTaxaCorretora,
        @NotNull Double apoQuantidadeCripto
) {
}
