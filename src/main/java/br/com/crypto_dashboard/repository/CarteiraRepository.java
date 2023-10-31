package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
