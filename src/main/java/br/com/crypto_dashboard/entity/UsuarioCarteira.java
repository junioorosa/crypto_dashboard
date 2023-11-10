package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario_carteira")
@Data
public class UsuarioCarteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "carteira_id")
    private Long carteiraId;

}
