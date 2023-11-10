package br.com.crypto_dashboard.infra.security;

import br.com.crypto_dashboard.entity.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    public final Usuario getUsuario() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}