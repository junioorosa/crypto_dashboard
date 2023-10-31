package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.AporteCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AporteCarteiraRepository extends JpaRepository<AporteCarteira, Long> {
    AporteCarteira getByAporteId(Long id);

    void deleteByAporteId(Long id);
}
