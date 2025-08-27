package com.muhasebe.insaat.controllers;

import com.muhasebe.insaat.model.Avans;
import com.muhasebe.insaat.services.AvansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avanslar")
public class AvansController {

    private final AvansService avansService;

    public AvansController(AvansService avansService) {
        this.avansService = avansService;
    }

    /** Avans kaydet/güncelle (id null ise insert) */
    @PostMapping
    public ResponseEntity<Avans> kaydet(@RequestBody Avans avans) {
        Avans saved = avansService.kaydet(avans);
        return ResponseEntity.ok(saved);
    }

    /** ID ile tek avans kaydı getir */
    @GetMapping("/{id}")
    public ResponseEntity<Avans> idIleGetir(@PathVariable Integer id) {
        return ResponseEntity.ok(avansService.idIleBul(id));
    }

    /** Listele: ?calisanId=... verilirse çalışana göre, verilmezse tümü */
    @GetMapping
    public List<Avans> listele(@RequestParam(required = false) Integer calisanId) {
        if (calisanId != null) {
            return avansService.calisanaGoreListele(calisanId);
        }
        return avansService.tumunuGetir();
    }

    /** Silme */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Integer id) {
        avansService.sil(id);
        return ResponseEntity.noContent().build();
    }

    /** Basit hata eşlemesi: bulunamayan kayıt -> 404 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.notFound().build();
    }
}