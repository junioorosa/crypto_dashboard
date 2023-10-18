package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.DadosCadastroCripto;
import br.com.crypto_dashboard.dto.DetalhamentoCriptoDto;
import br.com.crypto_dashboard.dto.ListaCriptoDto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.enums.CoinGeckoApi;
import br.com.crypto_dashboard.enums.CriptoConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class CriptoService {

    private final RequestService requestService;

    private final ObjectMapper objectMapper;

    public CriptoService(RequestService requestService, ObjectMapper objectMapper) {
        this.requestService = requestService;
        this.objectMapper = objectMapper;
    }

    public void cadastrar(Cripto cripto, DadosCadastroCripto dados) {
        var url = String.format(CoinGeckoApi.GET_COIN_DATA.getUrl(), CriptoConstants.USD.getValue(), dados.criIdApi());
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_DATA.getMethod());
        try {
            List<DetalhamentoCriptoDto> detalhamentoCriptoDto = objectMapper.readValue(response, new TypeReference<>() {});
            cripto.setCriIdApi(detalhamentoCriptoDto.get(0).id());
            cripto.setCriDescricao(detalhamentoCriptoDto.get(0).name());
            cripto.setCriImagem(getImagem(detalhamentoCriptoDto));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Page<ListaCriptoDto> getAllCriptoByApi(int pageNumber, int pageSize) {
        var url = String.format(CoinGeckoApi.GET_COIN_LIST.getUrl(), CriptoConstants.USD.getValue(), pageNumber, pageSize);
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_LIST.getMethod());
        try {
            List<ListaCriptoDto> listaCriptoDto = objectMapper.readValue(response, new TypeReference<>() {});
            return new PageImpl<>(listaCriptoDto, PageRequest.of(pageNumber, pageSize), listaCriptoDto.size());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private byte[] getImagem(List<DetalhamentoCriptoDto> detalhamentoCriptoDto) {
        try {
            URL imageUrl = new URL(detalhamentoCriptoDto.get(0).image());
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream().readAllBytes();
            } else {
                throw new IllegalStateException("Falha na obtenção da imagem: Código de resposta " + responseCode);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL inválida: " + e.getMessage());
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível obter a imagem: " + e.getMessage());
        }
    }
}
