package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Cripto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriptoRepository extends JpaRepository<Cripto, Long> {
}
