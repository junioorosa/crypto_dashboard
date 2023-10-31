package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioRepository extends JpaRepository<Cambio, Long> {
}
