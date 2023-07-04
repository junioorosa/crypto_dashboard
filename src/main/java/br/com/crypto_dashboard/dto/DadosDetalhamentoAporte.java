package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Aporte;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoAporte(
        Long id,
        String cripto,
        String cambio,
        BigDecimal precoCripto,
        LocalDateTime data,
        BigDecimal valorAportado,
        BigDecimal taxaCorretora,
        Double quantidadeCripto) {
    public DadosDetalhamentoAporte(Aporte aporte) {
        this(aporte.getId(), aporte.getCripto().getCriDescricao(), aporte.getCambio().getCamDescricao(), aporte.getApoPrecoCripto(),
                aporte.getApoData(), aporte.getApoValorAportado(), aporte.getApoTaxaCorretora(), aporte.getApoQuantidadeCripto());
    }
}
