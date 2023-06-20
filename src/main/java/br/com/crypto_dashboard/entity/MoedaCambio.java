package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "moeda_cambio")
@Data
public class MoedaCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cripto")
    private Cripto cripto;

    @ManyToOne
    @JoinColumn(name = "cambio")
    private Cambio cambio;

}
