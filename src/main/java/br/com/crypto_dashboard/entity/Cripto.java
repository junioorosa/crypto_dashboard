package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cripto")
@Data
public class Cripto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cri_descricao")
    private String criDescricao;

    @Column(name = "cri_id_api")
    private String criIdApi;

    @Column(name = "cri_imagem")
    private byte[] criImagem;

}
