package br.com.crypto_dashboard.repository;

import br.com.crypto_dashboard.entity.Cripto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriptoRepository extends JpaRepository<Cripto, Long> {
    void deleteByCriIdApi(String criIdApi);

    Optional<Cripto> findByCriIdApi(String criIdApi);
}
