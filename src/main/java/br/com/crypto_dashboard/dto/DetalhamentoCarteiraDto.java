package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Carteira;

public record DetalhamentoCarteiraDto(Long id, String carDescricao) {
    public DetalhamentoCarteiraDto(Carteira carteira) {
        this(carteira.getId(), carteira.getCarDescricao());
    }
}
