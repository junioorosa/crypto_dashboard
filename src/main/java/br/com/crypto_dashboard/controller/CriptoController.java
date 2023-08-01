package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.DadosCadastroCripto;
import br.com.crypto_dashboard.dto.DadosDetalhamentoCripto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.repository.CriptoRepository;
import br.com.crypto_dashboard.service.CriptoService;
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
@RequestMapping("/cripto")
public class CriptoController {

    @Autowired
    private CriptoRepository criptoRepository;

    @Autowired
    private CriptoService criptoService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid DadosCadastroCripto dados) {
        var cripto = new Cripto();
        criptoService.cadastrar(cripto, dados);
        BeanUtils.copyProperties(dados, cripto);
        criptoRepository.save(cripto);
        return ResponseEntity.ok(new DadosDetalhamentoCripto(cripto));
    }

//    @GetMapping
//    public ResponseEntity<Page<DadosDetalhamentoCarteira>> listar(@PageableDefault(sort = "{criDescricao}",size = 10) Pageable pageable) {
//    }


}
