package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.DadosCadastroAporte;
import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.repository.CambioRepository;
import br.com.crypto_dashboard.repository.CriptoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AporteService {

    @Autowired
    private CambioRepository cambioRepository;

    @Autowired
    private CriptoRepository criptoRepository;

    public void cadastrar(Aporte aporte, DadosCadastroAporte dados) {
        aporte.setCambio(cambioRepository.findById(dados.cambio_id()).orElseThrow());
        aporte.setCripto(criptoRepository.findById(dados.cripto_id()).orElseThrow());
        BeanUtils.copyProperties(dados, aporte);
    }
}
