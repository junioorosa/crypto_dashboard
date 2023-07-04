package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.DadosCadastroAporte;
import br.com.crypto_dashboard.dto.DadosDetalhamentoAporte;
import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.repository.AporteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aporte")
public class AporteController {

    @Autowired
    private AporteRepository aporteRepository;

    @PostMapping
    @Transactional
    private ResponseEntity<DadosDetalhamentoAporte> cadastrar(@RequestBody @Valid DadosCadastroAporte dados) {
        var aporte = new Aporte();
        BeanUtils.copyProperties(dados, aporte);
        aporteRepository.save(aporte);
        return ResponseEntity.ok(new DadosDetalhamentoAporte(aporte));
    }


}
