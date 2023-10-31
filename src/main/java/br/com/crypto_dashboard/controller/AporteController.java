package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.AtualizaAporteDto;
import br.com.crypto_dashboard.dto.CadastroAporteDto;
import br.com.crypto_dashboard.dto.DetalhamentoAporteDto;
import br.com.crypto_dashboard.repository.AporteCarteiraRepository;
import br.com.crypto_dashboard.repository.AporteRepository;
import br.com.crypto_dashboard.service.AporteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aporte")
public class AporteController {

    @Autowired
    private AporteService aporteService;

    @Autowired
    private AporteRepository aporteRepository;

    @Autowired
    private AporteCarteiraRepository aporteCarteiraRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<DetalhamentoAporteDto> cadastrar(@RequestBody @Valid CadastroAporteDto dados) {
        var aporte = aporteService.cadastrar(dados);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporte, aporteCarteiraRepository.getByAporteId(aporte.getId())));
    }

    @GetMapping
    public ResponseEntity<Page<DetalhamentoAporteDto>> listar(@PageableDefault(sort = {"apoData"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var aportes = aporteRepository.findAll(paginacao).map(aporte -> new DetalhamentoAporteDto(aporte, aporteCarteiraRepository.getByAporteId(aporte.getId())));
        return ResponseEntity.ok(aportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoAporteDto> detalhar(@PathVariable Long id) {
        var aporte = aporteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporte, aporteCarteiraRepository.getByAporteId(aporte.getId())));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoAporteDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaAporteDto dados) {
        aporteService.atualizaAporte(id, dados);
        var aporteCarteira = aporteCarteiraRepository.getByAporteId(id);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporteCarteira.getAporte(), aporteCarteira));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        var aporte = aporteRepository.getReferenceById(id);
        aporteService.excluiDadosPertinentesAoAporte(aporte);
        return ResponseEntity.ok().build();
    }
}
