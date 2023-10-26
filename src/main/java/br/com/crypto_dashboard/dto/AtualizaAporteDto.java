package br.com.crypto_dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtualizaAporteDto(
        Long carteiraId,
        BigDecimal apoPrecoCripto,
        LocalDateTime apoData,
        BigDecimal apoValorAportado,
        BigDecimal apoTaxaCorretora,
        Double apoQuantidadeCripto
) {
}
