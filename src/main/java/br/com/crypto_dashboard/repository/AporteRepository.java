package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Aporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AporteRepository extends JpaRepository<Aporte, Long> {
    List<Aporte> findAllByCriptoId(Long id);

    @Query(value = "FROM Aporte a WHERE EXISTS (FROM AporteCarteira ac WHERE ac.aporte.id = a.id AND ac.carteira.id = ?1)")
    List<Aporte> findAllByCarteiraId(Long id);
}
