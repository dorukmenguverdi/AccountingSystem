package com.muhasebe.insaat.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "gunluk_calisma",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"calisan_id", "tarih"})
        }
)
public class GunlukCalisma extends BaseEntity {

    /** Kime ait günlük kayıt */
    @ManyToOne(optional = false)
    @JoinColumn(name = "calisan_id", nullable = false)
    private Calisan calisan;

    /** Gün tarihi */
    @Column(nullable = false)
    private LocalDate tarih;

    /** Geldi/gelmedi*/
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CalismaDurum durum;
}
