package com.muhasebe.insaat.services;

import com.muhasebe.insaat.dto.RaporDto;
import com.muhasebe.insaat.model.Avans;
import com.muhasebe.insaat.model.Calisan;
import com.muhasebe.insaat.model.CalismaDurum;
import com.muhasebe.insaat.model.GunlukCalisma;
import com.muhasebe.insaat.repositories.AvansRepository;
import com.muhasebe.insaat.repositories.CalisanRepository;
import com.muhasebe.insaat.repositories.GunlukCalismaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RaporService {

    private final CalisanRepository calisanRepository;
    private final AvansRepository avansRepository;
    private final GunlukCalismaRepository gunlukCalismaRepository;

    public RaporService(CalisanRepository calisanRepository,
                        AvansRepository avansRepository,
                        GunlukCalismaRepository gunlukCalismaRepository) {
        this.calisanRepository = calisanRepository;
        this.avansRepository = avansRepository;
        this.gunlukCalismaRepository = gunlukCalismaRepository;
    }

    /** Belirli bir ay için TÜM çalışanların raporu. Örn: donem = 2025-08 */
    public List<RaporDto> raporAylik(YearMonth donem) {
        Objects.requireNonNull(donem, "donem zorunludur");
        LocalDate start = donem.atDay(1);
        LocalDate end   = donem.atEndOfMonth();

        return calisanRepository.findAll().stream()
                .map(c -> raporCalisanAralik(c, donem, start, end))
                .collect(Collectors.toList());
    }

    /** Belirli bir ay için TEK çalışanın raporu. */
    public RaporDto raporAylikCalisan(Integer calisanId, YearMonth donem) {
        Objects.requireNonNull(calisanId, "calisanId zorunludur");
        Objects.requireNonNull(donem, "donem zorunludur");
        LocalDate start = donem.atDay(1);
        LocalDate end   = donem.atEndOfMonth();

        Calisan calisan = calisanRepository.findById(calisanId)
                .orElseThrow(() -> new IllegalArgumentException("Calisan bulunamadı: " + calisanId));

        return raporCalisanAralik(calisan, donem, start, end);
    }

    // --------- Yardımcı ---------

    private RaporDto raporCalisanAralik(Calisan calisan, YearMonth donem, LocalDate start, LocalDate end) {
        Integer calisanId = calisan.getId();

        List<GunlukCalisma> gunlukler = gunlukCalismaRepository
                .findByCalisanIdAndTarihBetweenAndDurum(calisanId, start, end, CalismaDurum.GELDI);
        int gunSayisi = gunlukler.size();

        // İlgili ayın avans toplamı
        List<Avans> avanslar = avansRepository
                .findByCalisanIdAndTarihBetween(calisanId, start, end);

        BigDecimal toplamAvans = avanslar.stream()
                .map(a -> a.getTutar() == null ? BigDecimal.ZERO : a.getTutar())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gunlukUcret = calisan.getGunlukUcret() == null ? BigDecimal.ZERO : calisan.getGunlukUcret();

        return RaporDto.of(
                calisanId,
                // Calisan.java'da yalnızca "ad" var; raporda tek alan olarak bunu gösteriyoruz.
                calisan.getAd(),
                donem,
                gunSayisi,
                gunlukUcret,
                toplamAvans
        );
    }
}