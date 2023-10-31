package br.com.crypto_dashboard.service;

import br.com.crypto_dashboard.dto.CadastroCriptoDto;
import br.com.crypto_dashboard.dto.DetalhamentoCriptoApiDto;
import br.com.crypto_dashboard.dto.ListaCriptoApiDto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.enums.CoinGeckoApi;
import br.com.crypto_dashboard.enums.CriptoConstants;
import br.com.crypto_dashboard.repository.CriptoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CriptoRepository criptoRepository;

    public Cripto cadastrar(CadastroCriptoDto dados) {
        var cripto = new Cripto();
        var url = String.format(CoinGeckoApi.GET_COIN_DATA.getUrl(), CriptoConstants.USD.getValue(), dados.criIdApi());
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_DATA.getMethod());

        try {
            List<DetalhamentoCriptoApiDto> detalhamentoCriptoApiDto = objectMapper.readValue(response, new TypeReference<>() {});
            cripto.setCriIdApi(detalhamentoCriptoApiDto.get(0).id());
            cripto.setCriDescricao(detalhamentoCriptoApiDto.get(0).name());
            cripto.setCriImagem(getImagem(detalhamentoCriptoApiDto));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Cripto não encontrada");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return criptoRepository.save(cripto);
    }

    public Page<ListaCriptoApiDto> getAllCriptoByApi(int pageNumber, int pageSize) {
        var url = String.format(CoinGeckoApi.GET_COIN_LIST.getUrl(), CriptoConstants.USD.getValue(), pageNumber, pageSize);
        var response = requestService.doRequest(url, CoinGeckoApi.GET_COIN_LIST.getMethod());
        try {
            List<ListaCriptoApiDto> listaCriptoApiDto = objectMapper.readValue(response, new TypeReference<>() {});
            return new PageImpl<>(listaCriptoApiDto, PageRequest.of(pageNumber, pageSize), listaCriptoApiDto.size());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private byte[] getImagem(List<DetalhamentoCriptoApiDto> detalhamentoCriptoApiDto) {
        try {
            URL imageUrl = new URL(detalhamentoCriptoApiDto.get(0).image());
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
