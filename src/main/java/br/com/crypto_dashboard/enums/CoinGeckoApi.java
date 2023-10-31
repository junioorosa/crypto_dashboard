package br.com.crypto_dashboard.enums;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum CoinGeckoApi {
    BASE_URL("https://api.coingecko.com/api/v3/"),
    GET_COIN_DATA(BASE_URL.getUrl() + "coins/markets?vs_currency=%s&ids=%s&order=market_cap_desc&per_page=1&page=1&locale=en", HttpMethod.GET),
    GET_COIN_LIST(BASE_URL.getUrl() + "coins/markets?vs_currency=%s&order=market_cap_desc&page=%s&per_page=%s&locale=en", HttpMethod.GET);

    private final String url;
    HttpMethod method;

    CoinGeckoApi(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    CoinGeckoApi(String url) {
        this.url = url;
    }
}