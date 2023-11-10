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

@Service
public class AporteService {

    private final AporteRepository aporteRepository;

    private final CambioRepository cambioRepository;

    private final CriptoRepository criptoRepository;

    private final CarteiraRepository carteiraRepository;

    private final AporteCarteiraRepository aporteCarteiraRepository;

    private final CarteiraService carteiraService;

    private final UserSession userSession;

    public AporteService(AporteRepository aporteRepository, CambioRepository cambioRepository, CriptoRepository criptoRepository, CarteiraRepository carteiraRepository, AporteCarteiraRepository aporteCarteiraRepository, CarteiraService carteiraService, UserSession userSession) {
        this.aporteRepository = aporteRepository;
        this.cambioRepository = cambioRepository;
        this.criptoRepository = criptoRepository;
        this.carteiraRepository = carteiraRepository;
        this.aporteCarteiraRepository = aporteCarteiraRepository;
        this.carteiraService = carteiraService;
        this.userSession = userSession;
    }


    public Aporte cadastrar(CadastroAporteDto dados) {
        carteiraService.validaDadoUsuario(userSession.getUsuario().getId(), dados.carteiraId());
        var aporte = new Aporte();
        BeanUtils.copyProperties(dados, aporte);
        aporte.setCambio(cambioRepository.findById(dados.cambioId()).orElseThrow(EntityNotFoundException::new));
        aporte.setCripto(criptoRepository.findById(dados.criptoId()).orElseThrow(EntityNotFoundException::new));
        aporteRepository.save(aporte);

        var aporteCarteira = new AporteCarteira();
        aporteCarteira.setCarteira(carteiraRepository.findById(dados.carteiraId()).orElseThrow(EntityNotFoundException::new));
        aporteCarteira.setAporte(aporte);
        aporteCarteiraRepository.save(aporteCarteira);

        return aporte;
    }

    public void atualizaAporte(Long id, AtualizaAporteDto dados) {
        var aporte = aporteRepository.getReferenceById(id);
        aporte.setApoPrecoCripto(dados.apoPrecoCripto() != null ? dados.apoPrecoCripto() : aporte.getApoPrecoCripto());
        aporte.setApoData(dados.apoData() != null ? dados.apoData() : aporte.getApoData());
        aporte.setApoValorAportado(dados.apoValorAportado() != null ? dados.apoValorAportado() : aporte.getApoValorAportado());
        aporte.setApoTaxaCorretora(dados.apoTaxaCorretora() != null ? dados.apoTaxaCorretora() : aporte.getApoTaxaCorretora());
        aporte.setApoQuantidadeCripto(dados.apoQuantidadeCripto() != null ? dados.apoQuantidadeCripto() : aporte.getApoQuantidadeCripto());
        aporteRepository.save(aporte);

        var aporteCarteira = aporteCarteiraRepository.getByAporteId(aporte.getId());
        aporteCarteira.setCarteira(dados.carteiraId() != null ? carteiraRepository.findById(dados.carteiraId()).orElseThrow(EntityNotFoundException::new) : aporteCarteira.getCarteira());
        aporteCarteiraRepository.save(aporteCarteira);
    }

    public void excluiDadosPertinentesAoAporte(Aporte aporte) {
        if (aporteRepository.findAllByCriptoId(aporte.getCripto().getId()).size() == 1) {
            criptoRepository.deleteByCriIdApi(aporte.getCripto().getCriIdApi());
        }

        aporteCarteiraRepository.deleteByAporteId(aporte.getId());
        aporteRepository.delete(aporte);
    }
}
