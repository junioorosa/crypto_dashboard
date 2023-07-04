package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aporte")
@Data
public class Aporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cripto_id")
    private Cripto cripto;

    @ManyToOne
    @JoinColumn(name = "cambio_id")
    private Cambio cambio;

    @Column(name = "apo_preco_cripto")
    private BigDecimal apoPrecoCripto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apo_data")
    private LocalDateTime apoData;

    @Column(name = "apo_valor_aportado")
    private BigDecimal apoValorAportado;

    @Column(name = "apo_taxa_corretora")
    private BigDecimal apoTaxaCorretora;

    @Column(name = "apo_quantidade_cripto")
    private Double apoQuantidadeCripto;

}
