package br.com.crypto_dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CriptoCurrency(@JsonProperty("id") String id,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("name") String name,
                             @JsonProperty("image") String image,
                             @JsonProperty("current_price") double currentPrice,
                             @JsonProperty("market_cap") long marketCap,
                             @JsonProperty("market_cap_rank") int marketCapRank,
                             @JsonProperty("fully_diluted_valuation") long fullyDilutedValuation,
                             @JsonProperty("total_volume") long totalVolume,
                             @JsonProperty("high_24h") double high24h,
                             @JsonProperty("low_24h") double low24h,
                             @JsonProperty("price_change_24h") double priceChange24h,
                             @JsonProperty("price_change_percentage_24h") double priceChangePercentage24h,
                             @JsonProperty("market_cap_change_24h") long marketCapChange24h,
                             @JsonProperty("market_cap_change_percentage_24h") double marketCapChangePercentage24h,
                             @JsonProperty("circulating_supply") double circulatingSupply,
                             @JsonProperty("total_supply") double totalSupply,
                             @JsonProperty("max_supply") double maxSupply,
                             @JsonProperty("ath") double ath,
                             @JsonProperty("ath_change_percentage") double athChangePercentage,
                             @JsonProperty("ath_date") Date athDate,
                             @JsonProperty("atl") double atl,
                             @JsonProperty("atl_change_percentage") double atlChangePercentage,
                             @JsonProperty("atl_date") Date atlDate,
                             @JsonProperty("roi") Double roi,
                             @JsonProperty("last_updated") Date lastUpdated) {
}