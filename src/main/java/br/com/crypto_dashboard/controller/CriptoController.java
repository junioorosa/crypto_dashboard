package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.dto.CadastroCriptoDto;
import br.com.crypto_dashboard.dto.DetalhamentoCriptoDto;
import br.com.crypto_dashboard.dto.ListaCriptoApiDto;
import br.com.crypto_dashboard.entity.Cripto;
import br.com.crypto_dashboard.repository.CriptoRepository;
import br.com.crypto_dashboard.service.CriptoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cripto")
public class CriptoController {

    @Autowired
    private CriptoRepository criptoRepository;

    @Autowired
    private CriptoService criptoService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroCriptoDto dados) {
        var cripto = criptoService.cadastrar(dados);
        return ResponseEntity.ok(new DetalhamentoCriptoDto(cripto));
    }

    @GetMapping("/listar-cripto")
    public ResponseEntity<Page<ListaCriptoApiDto>> listarCriptoByApi(@RequestParam int page, @RequestParam int size) {
        Page<ListaCriptoApiDto> listaCripto = criptoService.getAllCriptoByApi(page, size);
        return ResponseEntity.ok(listaCripto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<Cripto> optionalCripto = criptoRepository.findById(id);
        if (optionalCripto.isPresent()) {
            Cripto cripto = optionalCripto.get();
            byte[] criptoImage = cripto.getCriImagem();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=cripto.png")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(criptoImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
