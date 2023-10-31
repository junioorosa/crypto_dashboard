package br.com.crypto_dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ListaCriptoApiDto(
    @JsonProperty("id") String id,
    @JsonProperty("symbol") String symbol,
    @JsonProperty("name") String name,
    @JsonProperty("image") String imageUrl
) {
}
