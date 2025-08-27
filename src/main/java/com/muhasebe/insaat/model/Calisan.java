package com.muhasebe.insaat.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calisan")
public class Calisan extends BaseEntity {

    /** Çalışanın adı (veya ad soyad) */
    @Column(nullable = false, length = 80)
    private String ad;

    /** Günlük ücret (TL) */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal gunlukUcret;
}