package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.UsuarioCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioCarteiraRepository extends JpaRepository<UsuarioCarteira, Long> {
    Optional<UsuarioCarteira> getByUsuarioIdAndCarteiraId(long usuarioId, Long carteiraId);
}
