package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.AtualizaCarteiraDto;
import br.com.crypto_dashboard.dto.CadastroCarteiraDto;
import br.com.crypto_dashboard.dto.DetalhamentoCarteiraDto;
import br.com.crypto_dashboard.entity.Carteira;
import br.com.crypto_dashboard.repository.AporteRepository;
import br.com.crypto_dashboard.repository.CarteiraRepository;
import br.com.crypto_dashboard.service.AporteService;
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

    @Autowired
    private AporteService aporteService;

    @Autowired
    private AporteRepository aporteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<DetalhamentoCarteiraDto> cadastrar(@RequestBody @Valid CadastroCarteiraDto dados) {
        var carteira = new Carteira();
        BeanUtils.copyProperties(dados, carteira);
        carteiraRepository.save(carteira);
        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @GetMapping
    public ResponseEntity<Page<DetalhamentoCarteiraDto>> listar(@PageableDefault(sort = {"carDescricao"}) Pageable paginacao) {
        var carteiras = carteiraRepository.findAll(paginacao).map(DetalhamentoCarteiraDto::new);
        return ResponseEntity.ok(carteiras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCarteiraDto> detalhar(@PathVariable Long id) {
        var carteira = carteiraRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoCarteiraDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaCarteiraDto dados) {
        var carteira = carteiraService.atualizaCarteira(id, dados);
        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        aporteRepository.findAllByCarteiraId(id).forEach(aporte -> aporteService.excluiDadosPertinentesAoAporte(aporte));

        var carteira = carteiraRepository.getReferenceById(id);
        carteiraRepository.delete(carteira);
        return ResponseEntity.noContent().build();
    }
}
