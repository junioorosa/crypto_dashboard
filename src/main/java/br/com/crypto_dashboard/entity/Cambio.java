package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cambio")
@Data
public class Cambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cam_descricao")
    private String camDescricao;

    @Column(name = "cam_imagem")
    private byte[] camImagem;

}

