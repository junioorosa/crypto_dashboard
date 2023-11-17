package br.com.crypto_dashboard.controller;

import br.com.crypto_dashboard.infra.dto.DadosAutenticacao;
import br.com.crypto_dashboard.infra.dto.DadosTokenJWT;
import br.com.crypto_dashboard.entity.Usuario;
import br.com.crypto_dashboard.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(),
                                                                          dadosAutenticacao.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
