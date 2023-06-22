package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "aporte")
@Data
public class Aporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cripto")
    private Cripto cripto;

    @ManyToOne
    @JoinColumn(name = "carteira")
    private Carteira carteira;

    @ManyToOne
    @JoinColumn(name = "cambio")
    private Cambio cambio;

    @Column(name = "apo_preco_cripto")
    private BigDecimal criPreco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apo_data")
    private Date apoData;

    @Column(name = "apo_valor")
    private BigDecimal apoValorAportado;

    @Column(name = "apo_taxa_corretora")
    private BigDecimal apoTaxaCorretora;

    @Column(name = "apo_quantidade_cripto")
    private Integer apoQuantidadeCripto;

}
