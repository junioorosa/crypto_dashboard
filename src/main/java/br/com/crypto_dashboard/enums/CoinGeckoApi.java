package br.com.crypto_dashboard.enums;

import br.com.crypto_dashboard.service.CriptoService;
import org.springframework.http.HttpMethod;

public enum CoinGeckoApi {
    GET_COIN_DATA(CriptoService.BASE_URL + "coins/markets?vs_currency=%s&ids=%s&order=market_cap_desc&per_page=50&locale=en", HttpMethod.GET);

    private final String url;
    private final HttpMethod method;

    CoinGeckoApi(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }
}