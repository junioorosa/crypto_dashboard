package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.AtualizaAporteDto;
import br.com.crypto_dashboard.dto.CadastroAporteDto;
import br.com.crypto_dashboard.dto.DetalhamentoAporteDto;
import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.infra.security.UserSession;
import br.com.crypto_dashboard.repository.AporteCarteiraRepository;
import br.com.crypto_dashboard.repository.AporteRepository;
import br.com.crypto_dashboard.service.AporteService;
import br.com.crypto_dashboard.service.CarteiraService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/aporte")
public class AporteController {

    private final AporteService aporteService;

    private final AporteRepository aporteRepository;

    private final AporteCarteiraRepository aporteCarteiraRepository;

    private final UserSession userSession;

    private final CarteiraService carteiraService;

    public AporteController(AporteService aporteService, AporteRepository aporteRepository, AporteCarteiraRepository aporteCarteiraRepository, UserSession userSession, CarteiraService carteiraService) {
        this.aporteService = aporteService;
        this.aporteRepository = aporteRepository;
        this.aporteCarteiraRepository = aporteCarteiraRepository;
        this.userSession = userSession;
        this.carteiraService = carteiraService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DetalhamentoAporteDto> cadastrar(@RequestBody @Valid CadastroAporteDto dados) {
        var aporte = aporteService.cadastrar(dados);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporteCarteiraRepository.getByAporteId(aporte.getId())));
    }

    @GetMapping
    public ResponseEntity<Page<DetalhamentoAporteDto>> listar(@PageableDefault(sort = {"apoData"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        Page<Aporte> aportes = aporteRepository.findAllByUsuarioId(paginacao, userSession.getUsuario().getId());
        return ResponseEntity.ok(aportes.map(aporte -> new DetalhamentoAporteDto(aporteCarteiraRepository.getByAporteId(aporte.getId()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoAporteDto> detalhar(@PathVariable Long id) {
        var aporte = aporteRepository.findByIdAndUsuarioId(id, userSession.getUsuario().getId()).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporteCarteiraRepository.getByAporteId(aporte.getId())));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoAporteDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaAporteDto dados) {
        var aporteCarteira = aporteService.atualizaAporte(id, dados);
        return ResponseEntity.ok(new DetalhamentoAporteDto(aporteCarteira));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        var aporteCarteira = aporteCarteiraRepository.getByAporteId(id);
        carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), Optional.ofNullable(aporteCarteira).orElseThrow(EntityNotFoundException::new).getCarteira().getId());
        var aporte = aporteRepository.findByIdAndUsuarioId(id, userSession.getUsuario().getId()).orElseThrow(EntityNotFoundException::new);
        aporteService.excluiDadosPertinentesAoAporte(aporte);
        return ResponseEntity.noContent().build();
    }
}
