package com.muhasebe.insaat.controllers;

import com.muhasebe.insaat.dto.RaporDto;
import com.muhasebe.insaat.services.RaporService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/rapor")
public class RaporController {

    private static final DateTimeFormatter YM_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    private final RaporService raporService;

    public RaporController(RaporService raporService) {
        this.raporService = raporService;
    }

    /**
     * Örn: GET /rapor/aylik?donem=2025-08
     * Dönem formatı: yyyy-MM
     */
    @GetMapping("/aylik")
    public ResponseEntity<List<RaporDto>> raporAylik(@RequestParam("donem") String donem) {
        YearMonth ym = parseDonem(donem);
        return ResponseEntity.ok(raporService.raporAylik(ym));
    }

    /**
     * Örn: GET /rapor/aylik/5?donem=2025-08
     * Belirli çalışanın aylık raporu.
     */
    @GetMapping("/aylik/{calisanId}")
    public ResponseEntity<RaporDto> raporAylikCalisan(@PathVariable Integer calisanId,
                                                      @RequestParam("donem") String donem) {
        YearMonth ym = parseDonem(donem);
        return ResponseEntity.ok(raporService.raporAylikCalisan(calisanId, ym));
    }

    // ---- Yardımcı ----
    private static YearMonth parseDonem(String raw) {
        try {
            return YearMonth.parse(raw, YM_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("donem formatı 'yyyy-MM' olmalıdır. Örn: 2025-08");
        }
    }
}