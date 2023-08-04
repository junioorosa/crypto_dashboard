package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.CriptoCurrency;
import br.com.crypto_dashboard.dto.DadosCadastroCripto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.enums.CoinGeckoApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriptoService {

    @Autowired
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String BASE_URL = "https://api.coingecko.com/api/v3/";

    public void cadastrar(Cripto cripto, DadosCadastroCripto dados) {
        var url = String.format(CoinGeckoApi.GET_COIN_DATA.getUrl(), "usd", dados.criIdApi());
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_DATA.getMethod());
        try {
            var criptoCurrency = objectMapper.readValue(response, CriptoCurrency.class);
            cripto.setCriIdApi(criptoCurrency.id());
            cripto.setCriDescricao(criptoCurrency.name());
//            cripto.setCriImagem(criptoCurrency.image());
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao cadastrar cripto");
        }




        System.out.println(response);
    }
}
