package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Carteira;
import org.springframework.data.domain.Page;

public record DetalhamentoCarteiraDto(Long id, String carDescricao) {
    public DetalhamentoCarteiraDto(Carteira carteira) {
        this(carteira.getId(), carteira.getCarDescricao());
    }

    public static Page<DetalhamentoCarteiraDto> converter(Page<Carteira> carteiras) {
        return carteiras.map(DetalhamentoCarteiraDto::new);
    }
}
