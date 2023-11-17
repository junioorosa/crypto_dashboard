package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.AtualizaCarteiraDto;
import br.com.crypto_dashboard.dto.CadastroCarteiraDto;
import br.com.crypto_dashboard.dto.DetalhamentoCarteiraDto;
import br.com.crypto_dashboard.entity.Carteira;
import br.com.crypto_dashboard.entity.UsuarioCarteira;
import br.com.crypto_dashboard.infra.security.UserSession;
import br.com.crypto_dashboard.repository.AporteRepository;
import br.com.crypto_dashboard.repository.CarteiraRepository;
import br.com.crypto_dashboard.repository.UsuarioCarteiraRepository;
import br.com.crypto_dashboard.service.AporteService;
import br.com.crypto_dashboard.service.CarteiraService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final CarteiraRepository carteiraRepository;

    private final CarteiraService carteiraService;

    private final AporteService aporteService;

    private final AporteRepository aporteRepository;

    private final UserSession userSession;

    private final UsuarioCarteiraRepository usuarioCarteiraRepository;

    public CarteiraController(UserSession userSession, UsuarioCarteiraRepository usuarioCarteiraRepository, CarteiraRepository carteiraRepository, CarteiraService carteiraService, AporteService aporteService, AporteRepository aporteRepository) {
        this.userSession = userSession;
        this.usuarioCarteiraRepository = usuarioCarteiraRepository;
        this.carteiraRepository = carteiraRepository;
        this.carteiraService = carteiraService;
        this.aporteService = aporteService;
        this.aporteRepository = aporteRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DetalhamentoCarteiraDto> cadastrar(@RequestBody @Valid CadastroCarteiraDto dados) {
        var carteira = new Carteira();
        BeanUtils.copyProperties(dados, carteira);
        carteiraRepository.save(carteira);

        var user = userSession.getUsuario();
        var usuarioCarteira = new UsuarioCarteira();
        usuarioCarteira.setUsuarioId(user.getId());
        usuarioCarteira.setCarteiraId(carteira.getId());
        usuarioCarteiraRepository.save(usuarioCarteira);

        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @GetMapping
    public ResponseEntity<Page<DetalhamentoCarteiraDto>> listar(@PageableDefault(sort = {"carDescricao"}) Pageable paginacao) {
        Page<Carteira> carteiras = carteiraRepository.findAllByUsuarioId(userSession.getUsuario().getId(), paginacao);
        return ResponseEntity.ok(DetalhamentoCarteiraDto.converter(carteiras));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoCarteiraDto> detalhar(@PathVariable Long id) {
        carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), id);
        var carteira = carteiraRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoCarteiraDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaCarteiraDto dados) {
        carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), id);
        var carteira = carteiraService.atualizaCarteira(id, dados);
        return ResponseEntity.ok(new DetalhamentoCarteiraDto(carteira));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        var usuarioCarteira = carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), id);
        usuarioCarteiraRepository.delete(usuarioCarteira);
        aporteRepository.findAllByCarteiraId(id).forEach(aporteService::excluiDadosPertinentesAoAporte);
        var carteira = carteiraRepository.getReferenceById(id);
        carteiraRepository.delete(carteira);
        return ResponseEntity.noContent().build();
    }
}
