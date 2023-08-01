package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.DadosCadastroCripto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.enums.CoinGeckoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriptoService {

    @Autowired
    private RequestService requestService;

    public static final String BASE_URL = "https://api.coingecko.com/api/v3/";

    public void cadastrar(Cripto cripto, DadosCadastroCripto dados) {
        var url = String.format(CoinGeckoApi.GET_COIN_DATA.getUrl(), "usd", dados.criIdApi());
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_DATA.getMethod());
        System.out.println(response);
    }
}
