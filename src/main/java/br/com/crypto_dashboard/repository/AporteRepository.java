package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Aporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AporteRepository extends JpaRepository<Aporte, Long> {
}
