package com.muhasebe.insaat.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Rapor çıktısı (Entity DEĞİL). Veritabanına bağlanmaz.
 * - toplamHakEdis = gunlukUcret * gunSayisi
 * - netMaas       = toplamHakEdis - toplamAvans
 */
public record RaporDto(
        Integer calisanId,
        String adSoyad,
        YearMonth donem,            // Örn: 2025-08
        int gunSayisi,              // Donem içinde çalıştığı gün sayısı
        BigDecimal gunlukUcret,     // Çalışanın günlük ücreti
        BigDecimal toplamHakEdis,   // Hesaplanan hakediş
        BigDecimal toplamAvans,     // Donem içindeki avansların toplamı
        BigDecimal netMaas          // toplamHakEdis - toplamAvans
) {

    public RaporDto {
        // Zorunlu alan kontrolleri
        Objects.requireNonNull(calisanId, "calisanId zorunludur");
        Objects.requireNonNull(adSoyad, "adSoyad zorunludur");
        Objects.requireNonNull(donem, "donem zorunludur");
        Objects.requireNonNull(gunlukUcret, "gunlukUcret zorunludur");
        Objects.requireNonNull(toplamHakEdis, "toplamHakEdis zorunludur");
        Objects.requireNonNull(toplamAvans, "toplamAvans zorunludur");
        Objects.requireNonNull(netMaas, "netMaas zorunludur");

        if (gunSayisi < 0) {
            throw new IllegalArgumentException("gunSayisi negatif olamaz");
        }
        if (gunlukUcret.signum() < 0) {
            throw new IllegalArgumentException("gunlukUcret negatif olamaz");
        }
        if (toplamAvans.signum() < 0) {
            throw new IllegalArgumentException("toplamAvans negatif olamaz");
        }

        // Para birimi için 2 ondalık standardizasyon
        gunlukUcret   = gunlukUcret.setScale(2, RoundingMode.HALF_UP);
        BigDecimal hak = toplamHakEdis.setScale(2, RoundingMode.HALF_UP);
        BigDecimal avs = toplamAvans.setScale(2, RoundingMode.HALF_UP);
        BigDecimal net = netMaas.setScale(2, RoundingMode.HALF_UP);

        // record compact constructor içinde yeniden atama
        toplamHakEdis = hak;
        toplamAvans   = avs;
        netMaas       = net;
    }

    /**
     * Pratik kurucu: dışarıdan yalnızca hesap için gereken girdileri alır,
     * hakediş ve net maaşı kendi içinde hesaplayıp DTO üretir.
     */
    public static RaporDto of(Integer calisanId,
                              String adSoyad,
                              YearMonth donem,
                              int gunSayisi,
                              BigDecimal gunlukUcret,
                              BigDecimal toplamAvans) {

        Objects.requireNonNull(gunlukUcret, "gunlukUcret zorunludur");
        Objects.requireNonNull(toplamAvans, "toplamAvans zorunludur");

        BigDecimal toplamHakEdis = gunlukUcret.multiply(BigDecimal.valueOf(gunSayisi));
        BigDecimal netMaas       = toplamHakEdis.subtract(toplamAvans);

        return new RaporDto(
                calisanId,
                adSoyad,
                donem,
                gunSayisi,
                gunlukUcret,
                toplamHakEdis,
                toplamAvans,
                netMaas
        );
    }
}