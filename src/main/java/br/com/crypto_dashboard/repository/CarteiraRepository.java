package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Carteira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    @Query("SELECT c FROM Carteira c WHERE EXISTS (SELECT uc FROM UsuarioCarteira uc WHERE uc.usuarioId = :id AND uc.carteiraId = c.id)")
    Page<Carteira> findAllByUsuarioId(long id, Pageable paginacao);

}
