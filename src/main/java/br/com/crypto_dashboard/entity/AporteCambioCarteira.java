package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aporte_cambio_carteira")
@Data
public class AporteCambioCarteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aporte")
    private Aporte aporte;

    @ManyToOne
    @JoinColumn(name = "cambio")
    private Cambio cambio;

    @ManyToOne
    @JoinColumn(name = "carteira")
    private Carteira carteira;

}
