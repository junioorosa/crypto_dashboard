package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.entity.AporteCarteira;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalhamentoAporteDto(
        Long id,
        String criDescricao,
        String camDescricao,
        BigDecimal apoPrecoCripto,
        LocalDateTime apoData,
        BigDecimal apoValorAportado,
        BigDecimal apoTaxaCorretora,
        Double apoQuantidadeCripto,
        String carDescricao
) {
    public DetalhamentoAporteDto(Aporte aporte, AporteCarteira aporteCarteira) {
        this(aporte.getId(), aporte.getCripto().getCriDescricao(), aporte.getCambio().getCamDescricao(), aporte.getApoPrecoCripto(),
                aporte.getApoData(), aporte.getApoValorAportado(), aporte.getApoTaxaCorretora(), aporte.getApoQuantidadeCripto(),
                aporteCarteira.getCarteira().getCarDescricao());
    }
}
