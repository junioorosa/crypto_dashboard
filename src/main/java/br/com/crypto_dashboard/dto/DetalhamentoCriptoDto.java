package br.com.crypto_dashboard.dto;

import br.com.crypto_dashboard.entity.Cripto;

import java.util.Base64;

public record DetalhamentoCriptoDto(
        Long id,
        String criDescricao
) {
    public DetalhamentoCriptoDto(Cripto cripto) {
        this(cripto.getId(), cripto.getCriDescricao());
    }
}
