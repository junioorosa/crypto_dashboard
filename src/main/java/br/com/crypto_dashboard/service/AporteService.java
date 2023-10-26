package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.AtualizaAporteDto;
import br.com.crypto_dashboard.dto.CadastroAporteDto;
import br.com.crypto_dashboard.entity.Aporte;
import br.com.crypto_dashboard.entity.AporteCarteira;
import br.com.crypto_dashboard.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AporteService {

    @Autowired
    private AporteRepository aporteRepository;

    @Autowired
    private CambioRepository cambioRepository;

    @Autowired
    private CriptoRepository criptoRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private AporteCarteiraRepository aporteCarteiraRepository;


    public Aporte cadastrar(CadastroAporteDto dados) {
        var aporte = new Aporte();
        BeanUtils.copyProperties(dados, aporte);
        aporte.setCambio(cambioRepository.findById(dados.cambioId()).orElseThrow(() -> new IllegalArgumentException("Câmbio não encontrado")));
        aporte.setCripto(criptoRepository.findById(dados.criptoId()).orElseThrow(() -> new IllegalArgumentException("Cripto não encontrada")));
        aporteRepository.save(aporte);

        var aporteCarteira = new AporteCarteira();
        aporteCarteira.setCarteira(carteiraRepository.findById(dados.carteiraId()).orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada")));
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
        aporteCarteira.setCarteira(dados.carteiraId() != null ? carteiraRepository.findById(dados.carteiraId()).orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada")) : aporteCarteira.getCarteira());
        aporteCarteiraRepository.save(aporteCarteira);
    }
}
