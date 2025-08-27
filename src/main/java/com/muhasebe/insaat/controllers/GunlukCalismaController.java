package com.muhasebe.insaat.controllers;

import com.muhasebe.insaat.model.GunlukCalisma;
import com.muhasebe.insaat.services.GunlukCalismaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gunluk-calisma")
public class GunlukCalismaController {

    private final GunlukCalismaService gunlukCalismaService;

    public GunlukCalismaController(GunlukCalismaService gunlukCalismaService) {
        this.gunlukCalismaService = gunlukCalismaService;
    }

    /** Kaydet / güncelle (id null ise insert) */
    @PostMapping
    public ResponseEntity<GunlukCalisma> kaydet(@RequestBody GunlukCalisma kayit) {
        GunlukCalisma saved = gunlukCalismaService.kaydet(kayit);
        return ResponseEntity.ok(saved);
    }

    /** ID ile tek kayıt getir */
    @GetMapping("/{id}")
    public ResponseEntity<GunlukCalisma> idIleGetir(@PathVariable Integer id) {
        return ResponseEntity.ok(gunlukCalismaService.idIleBul(id));
    }

    /**
     * Listeleme:
     *  - ?calisanId=...           -> çalışana göre
     *  - ?tarih=YYYY-MM-DD        -> tarihe göre
     *  - ?calisanId=...&tarih=... -> ikisine göre (kesişim)
     *  - parametre yok            -> tümü
     */
    @GetMapping
    public List<GunlukCalisma> listele(
            @RequestParam(required = false) Integer calisanId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih) {

        if (calisanId != null && tarih != null) {
            // İki koşulu birlikte uygula (repo'da birleşik sorgu yoksa stream ile filtrele)
            return gunlukCalismaService.calisanaGoreListele(calisanId).stream()
                    .filter(g -> tarih.equals(g.getTarih()))
                    .collect(Collectors.toList());
        }
        if (calisanId != null) {
            return gunlukCalismaService.calisanaGoreListele(calisanId);
        }
        if (tarih != null) {
            return gunlukCalismaService.tariheGoreListele(tarih);
        }
        return gunlukCalismaService.tumunuGetir();
    }

    /** Silme */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Integer id) {
        gunlukCalismaService.sil(id);
        return ResponseEntity.noContent().build();
    }

    /** Basit hata eşlemesi: bulunamayan kayıt -> 404 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.notFound().build();
    }
}
