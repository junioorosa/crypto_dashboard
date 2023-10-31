package br.com.crypto_dashboard.dto;

public record RoiDto(
        double times,
        String currency,
        double percentage
) {
}
