package br.com.crypto_dashboard.dto;

public record DadosDetalhamentoCarteira(Long id, String carDescricao) {
    public DadosDetalhamentoCarteira(br.com.crypto_dashboard.entity.Carteira carteira) {
        this(carteira.getId(), carteira.getCarDescricao());
    }
}
