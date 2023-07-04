package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.DadosAtualizaCarteira;
import br.com.crypto_dashboard.entity.Carteira;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    public Carteira atualizaInformacoes(Carteira carteira, DadosAtualizaCarteira dados) {
        if (dados.carDescricao() != null) {
            carteira.setCarDescricao(dados.carDescricao());
        }
        return carteira;
    }

}
