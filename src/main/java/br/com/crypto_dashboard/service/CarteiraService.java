package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.AtualizaCarteiraDto;
import br.com.crypto_dashboard.entity.Carteira;
import br.com.crypto_dashboard.entity.UsuarioCarteira;
import br.com.crypto_dashboard.infra.exception.UserDoesNotOwnResourceException;
import br.com.crypto_dashboard.repository.CarteiraRepository;
import br.com.crypto_dashboard.repository.UsuarioCarteiraRepository;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;

    private final UsuarioCarteiraRepository usuarioCarteiraRepository;

    public CarteiraService(CarteiraRepository carteiraRepository, UsuarioCarteiraRepository usuarioCarteiraRepository) {
        this.carteiraRepository = carteiraRepository;
        this.usuarioCarteiraRepository = usuarioCarteiraRepository;
    }

    public Carteira atualizaCarteira(Long id, AtualizaCarteiraDto dados) {
        var carteira = carteiraRepository.getReferenceById(id);
        carteira.setCarDescricao(dados.carDescricao());
        return carteira;
    }

    public UsuarioCarteira validaDadoUsuario(Long userId, Long id) {
        return usuarioCarteiraRepository.getByUsuarioIdAndCarteiraId(userId, id).orElseThrow(UserDoesNotOwnResourceException::new);
    }
}
