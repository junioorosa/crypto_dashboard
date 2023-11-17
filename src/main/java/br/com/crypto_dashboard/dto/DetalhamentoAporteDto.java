package br.com.crypto_dashboard.dto;

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
    public DetalhamentoAporteDto(AporteCarteira aporteCarteira) {
        this(aporteCarteira.getAporte().getId(), aporteCarteira.getAporte().getCripto().getCriDescricao(), aporteCarteira.getAporte().getCambio().getCamDescricao(),
                aporteCarteira.getAporte().getApoPrecoCripto(), aporteCarteira.getAporte().getApoData(), aporteCarteira.getAporte().getApoValorAportado(),
                aporteCarteira.getAporte().getApoTaxaCorretora(), aporteCarteira.getAporte().getApoQuantidadeCripto(), aporteCarteira.getCarteira().getCarDescricao());
    }
}
