package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.DadosAtualizaCarteira;
import br.com.crypto_dashboard.entity.Carteira;
import br.com.crypto_dashboard.repository.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    public Carteira atualizaCarteira(Long id, DadosAtualizaCarteira dados) {
        var carteira = carteiraRepository.getReferenceById(id);
        carteira.setCarDescricao(dados.carDescricao());
        return carteira;
    }

}
