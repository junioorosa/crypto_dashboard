package br.com.crypto_dashboard.enums;

import lombok.Getter;

@Getter
public enum CriptoConstants {
    USD("usd"),
    BRL("brl"),
    EUR("eur");

    private final String value;

    CriptoConstants(String value) {
        this.value = value;
    }
}
