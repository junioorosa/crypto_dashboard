package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.DadosAtualizaCarteira;
import br.com.crypto_dashboard.dto.DadosCadastroCarteira;
import br.com.crypto_dashboard.dto.DadosDetalhamentoCarteira;
import br.com.crypto_dashboard.entity.Carteira;
import br.com.crypto_dashboard.repository.CarteiraRepository;
import br.com.crypto_dashboard.service.CarteiraService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCarteira> cadastrar(@RequestBody @Valid DadosCadastroCarteira dados) {
        var carteira = new Carteira();
        BeanUtils.copyProperties(dados, carteira);
        carteiraRepository.save(carteira);
        return ResponseEntity.ok(new DadosDetalhamentoCarteira(carteira));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCarteira>> listar(@PageableDefault(sort = {"carDescricao"}, size = 10) Pageable paginacao) {
        var carteiras = carteiraRepository.findAll(paginacao).map(DadosDetalhamentoCarteira::new);
        return ResponseEntity.ok(carteiras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCarteira> detalhar(@PathVariable Long id) {
        var carteira = carteiraRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoCarteira(carteira));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCarteira> atualizar(@RequestBody @Valid DadosAtualizaCarteira dados) {
        var carteira = carteiraRepository.getReferenceById(dados.id());
        carteira = carteiraService.atualizaInformacoes(carteira, dados);
        return ResponseEntity.ok(new DadosDetalhamentoCarteira(carteira));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        var carteira = carteiraRepository.getReferenceById(id);
        carteiraRepository.delete(carteira);
        return ResponseEntity.noContent().build();
    }

}
