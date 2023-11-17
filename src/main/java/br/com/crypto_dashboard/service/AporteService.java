package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.AtualizaAporteDto;
import br.com.crypto_dashboard.dto.CadastroAporteDto;
import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.entity.AporteCarteira;
import br.com.crypto_dashboard.infra.security.UserSession;
import br.com.crypto_dashboard.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AporteService {

    private final AporteRepository aporteRepository;

    private final CambioRepository cambioRepository;

    private final CriptoRepository criptoRepository;

    private final CriptoService criptoService;

    private final CarteiraRepository carteiraRepository;

    private final CarteiraService carteiraService;

    private final AporteCarteiraRepository aporteCarteiraRepository;

    private final UserSession userSession;

    public AporteService(AporteRepository aporteRepository, CambioRepository cambioRepository, CriptoRepository criptoRepository, CriptoService criptoService, CarteiraRepository carteiraRepository, CarteiraService carteiraService, AporteCarteiraRepository aporteCarteiraRepository, UserSession userSession) {
        this.aporteRepository = aporteRepository;
        this.cambioRepository = cambioRepository;
        this.criptoRepository = criptoRepository;
        this.criptoService = criptoService;
        this.carteiraRepository = carteiraRepository;
        this.carteiraService = carteiraService;
        this.aporteCarteiraRepository = aporteCarteiraRepository;
        this.userSession = userSession;
    }


    public Aporte cadastrar(CadastroAporteDto dados) {
        carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), dados.carteiraId());
        var cripto = criptoService.cadastrar(dados.criIdApi());

        var aporte = new Aporte();
        BeanUtils.copyProperties(dados, aporte);
        aporte.setCambio(cambioRepository.findById(dados.cambioId()).orElseThrow(EntityNotFoundException::new));
        aporte.setCripto(Optional.ofNullable(cripto).orElseThrow(EntityNotFoundException::new));
        aporteRepository.save(aporte);

        var aporteCarteira = new AporteCarteira();
        aporteCarteira.setCarteira(carteiraRepository.findById(dados.carteiraId()).orElseThrow(EntityNotFoundException::new));
        aporteCarteira.setAporte(aporte);
        aporteCarteiraRepository.save(aporteCarteira);

        return aporte;
    }

    public AporteCarteira atualizaAporte(Long id, AtualizaAporteDto dados) {
        carteiraService.validarCarteiraDoUsuario(userSession.getUsuario().getId(), dados.carteiraId());
        var aporte = aporteRepository.findByIdAndUsuarioId(id, userSession.getUsuario().getId()).orElseThrow(EntityNotFoundException::new);

        aporte.setApoPrecoCripto(dados.apoPrecoCripto() != null ? dados.apoPrecoCripto() : aporte.getApoPrecoCripto());
        aporte.setApoData(dados.apoData() != null ? dados.apoData() : aporte.getApoData());
        aporte.setApoValorAportado(dados.apoValorAportado() != null ? dados.apoValorAportado() : aporte.getApoValorAportado());
        aporte.setApoTaxaCorretora(dados.apoTaxaCorretora() != null ? dados.apoTaxaCorretora() : aporte.getApoTaxaCorretora());
        aporte.setApoQuantidadeCripto(dados.apoQuantidadeCripto() != null ? dados.apoQuantidadeCripto() : aporte.getApoQuantidadeCripto());
        aporteRepository.save(aporte);

        var aporteCarteira = aporteCarteiraRepository.getByAporteId(aporte.getId());
        aporteCarteira.setCarteira(dados.carteiraId() != null ? carteiraRepository.findById(dados.carteiraId()).orElseThrow(EntityNotFoundException::new) : aporteCarteira.getCarteira());
        return aporteCarteiraRepository.save(aporteCarteira);
    }

    public void excluiDadosPertinentesAoAporte(Aporte aporte) {
        if (aporteRepository.findAllByCriptoId(aporte.getCripto().getId()).size() == 1) {
            criptoRepository.deleteByCriIdApi(aporte.getCripto().getCriIdApi());
        }

        aporteCarteiraRepository.deleteByAporteId(aporte.getId());
        aporteRepository.delete(aporte);
    }
}
