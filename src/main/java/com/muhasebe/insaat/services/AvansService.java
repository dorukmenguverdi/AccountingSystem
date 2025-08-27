package com.muhasebe.insaat.services;

import com.muhasebe.insaat.model.Avans;
import com.muhasebe.insaat.repositories.AvansRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AvansService {

    private final AvansRepository avansRepository;

    public AvansService(AvansRepository avansRepository) {
        this.avansRepository = avansRepository;
    }

    /** Kaydet / güncelle (id null ise yeni kayıt) */
    @Transactional
    public Avans kaydet(Avans avans) {
        if (avans.getCalisan() == null || avans.getCalisan().getId() == null) {
            throw new IllegalArgumentException("Geçerli bir çalışan (id) zorunludur.");
        }
        if (avans.getTarih() == null) {
            throw new IllegalArgumentException("Tarih zorunludur (nullable=false).");
        }
        BigDecimal tutar = avans.getTutar();
        if (tutar == null || tutar.signum() <= 0) {
            throw new IllegalArgumentException("Avans tutarı 0'dan büyük olmalıdır.");
        }
        return avansRepository.save(avans);
    }

    /** ID ile tek avans kaydı getir (yoksa hata) */
    @Transactional(readOnly = true)
    public Avans idIleBul(Integer id) {
        return avansRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Avans kaydı bulunamadı. id=" + id));
    }

    /** Tüm avans kayıtları */
    @Transactional(readOnly = true)
    public List<Avans> tumunuGetir() {
        return avansRepository.findAll();
    }

    /** Çalışana göre avans kayıtları */
    @Transactional(readOnly = true)
    public List<Avans> calisanaGoreListele(Integer calisanId) {
        return avansRepository.findByCalisanId(calisanId);
    }

    /** Kayıt sil (yoksa hata) */
    @Transactional
    public void sil(Integer id) {
        if (!avansRepository.existsById(id)) {
            throw new IllegalArgumentException("Silinecek avans kaydı bulunamadı. id=" + id);
        }
        avansRepository.deleteById(id);
    }
}