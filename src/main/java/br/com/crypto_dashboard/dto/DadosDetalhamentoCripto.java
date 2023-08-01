package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Cripto;

import java.util.Base64;

public record DadosDetalhamentoCripto(Long id, String criDescricao, String criImagem) {
    public DadosDetalhamentoCripto(Cripto cripto) {
        this(cripto.getId(), cripto.getCriDescricao(), Base64.getEncoder().encodeToString(cripto.getCriImagem()));
    }
}
