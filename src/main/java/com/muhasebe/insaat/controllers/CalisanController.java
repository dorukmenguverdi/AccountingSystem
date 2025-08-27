package com.muhasebe.insaat.controllers;

import com.muhasebe.insaat.model.Calisan;
import com.muhasebe.insaat.services.CalisanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calisanlar")
public class CalisanController {

    private final CalisanService calisanService;

    public CalisanController(CalisanService calisanService) {
        this.calisanService = calisanService;
    }

    /** Çalışan kaydet/güncelle */
    @PostMapping
    public ResponseEntity<Calisan> kaydet(@RequestBody Calisan calisan) {
        Calisan saved = calisanService.kaydet(calisan);
        return ResponseEntity.ok(saved);
    }

    /** ID ile tek çalışan getir */
    @GetMapping("/{id}")
    public ResponseEntity<Calisan> idIleGetir(@PathVariable Integer id) {
        return ResponseEntity.ok(calisanService.idIleBul(id));
    }

    /** Tüm çalışanları getir */
    @GetMapping
    public List<Calisan> tumunuGetir() {
        return calisanService.tumunuGetir();
    }

    /** Çalışan sil */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Integer id) {
        calisanService.sil(id);
        return ResponseEntity.noContent().build();
    }

    /** Basit hata eşlemesi: bulunamayan kayıt -> 404 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.notFound().build();
    }
}
