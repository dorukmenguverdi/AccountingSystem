package com.muhasebe.insaat.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avans")
public class Avans extends BaseEntity {

    /** Avansı alan çalışan */
    @ManyToOne(optional = false)
    @JoinColumn(name = "calisan_id", nullable = false)
    private Calisan calisan;

    /** Avans tutarı (TL) */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal tutar;

    /** Avans tarihi */
    @Column(nullable = false)
    private LocalDate tarih;

}