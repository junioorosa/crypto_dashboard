package br.com.crypto_dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aporte_carteira")
@Data
public class AporteCarteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aporte_id")
    private Aporte aporte;

    @ManyToOne
    @JoinColumn(name = "carteira_id")
    private Carteira carteira;

}
