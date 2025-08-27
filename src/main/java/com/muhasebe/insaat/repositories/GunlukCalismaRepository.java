package com.muhasebe.insaat.repositories;

import com.muhasebe.insaat.model.CalismaDurum;
import com.muhasebe.insaat.model.GunlukCalisma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GunlukCalismaRepository extends JpaRepository<GunlukCalisma, Integer> {

    List<GunlukCalisma> findByCalisanId(Integer calisanId);

    List<GunlukCalisma> findByTarih(LocalDate tarih);

    List<GunlukCalisma> findByCalisanIdAndTarihBetweenAndDurum(Integer calisanId, LocalDate start, LocalDate end, CalismaDurum durum);
}
