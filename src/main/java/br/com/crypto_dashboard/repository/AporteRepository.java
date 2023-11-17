package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Aporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AporteRepository extends JpaRepository<Aporte, Long> {
    List<Aporte> findAllByCriptoId(Long id);

    @Query(value = "FROM Aporte a WHERE EXISTS (FROM AporteCarteira ac WHERE ac.aporte.id = a.id AND ac.carteira.id = ?1)")
    List<Aporte> findAllByCarteiraId(Long id);

    @Query(value = "FROM Aporte a WHERE EXISTS (FROM AporteCarteira ac WHERE ac.aporte.id = a.id " +
            "AND EXISTS (FROM UsuarioCarteira uc WHERE uc.carteiraId = ac.carteira.id AND uc.usuarioId = ?1))")
    Page<Aporte> findAllByUsuarioId(Pageable paginacao, long id);

    @Query(value = "FROM Aporte a WHERE a.id = ?1 " +
            "AND EXISTS (FROM AporteCarteira ac WHERE ac.aporte.id = a.id " +
            "AND EXISTS (" +
            "FROM UsuarioCarteira uc WHERE uc.carteiraId = ac.carteira.id AND uc.usuarioId = ?2))")
    Optional<Aporte> findByIdAndUsuarioId(Long id, long usuarioId);
}
