package com.muhasebe.insaat.services;

import com.muhasebe.insaat.model.Calisan;
import com.muhasebe.insaat.model.GunlukCalisma;
import com.muhasebe.insaat.model.CalismaDurum;
import com.muhasebe.insaat.repositories.CalisanRepository;
import com.muhasebe.insaat.repositories.GunlukCalismaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GunlukCalismaService {

    private final GunlukCalismaRepository gunlukCalismaRepository;
    private final CalisanRepository calisanRepository;

    public GunlukCalismaService(GunlukCalismaRepository gunlukCalismaRepository,
                                CalisanRepository calisanRepository) {
        this.gunlukCalismaRepository = gunlukCalismaRepository;
        this.calisanRepository = calisanRepository;
    }

    /** Body’den komple entity alıp kaydet/güncelle (id null ise insert) */
    @Transactional
    public GunlukCalisma kaydet(GunlukCalisma kayit) {
        if (kayit.getTarih() == null) {
            throw new IllegalArgumentException("Tarih zorunludur (nullable=false).");
        }
        if (kayit.getCalisan() == null || kayit.getCalisan().getId() == null) {
            throw new IllegalArgumentException("Geçerli bir çalışan (id) zorunludur.");
        }
        return gunlukCalismaRepository.save(kayit);
    }

    /** ID ile tek kayıt getir (yoksa hata) */
    @Transactional(readOnly = true)
    public GunlukCalisma idIleBul(Integer id) {
        return gunlukCalismaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Günlük çalışma kaydı bulunamadı. id=" + id));
    }

    /** Tüm günlük çalışma kayıtları */
    @Transactional(readOnly = true)
    public List<GunlukCalisma> tumunuGetir() {
        return gunlukCalismaRepository.findAll();
    }

    /** Çalışana göre listele */
    @Transactional(readOnly = true)
    public List<GunlukCalisma> calisanaGoreListele(Integer calisanId) {
        return gunlukCalismaRepository.findByCalisanId(calisanId);
    }

    /** Tarihe göre listele */
    @Transactional(readOnly = true)
    public List<GunlukCalisma> tariheGoreListele(LocalDate tarih) {
        return gunlukCalismaRepository.findByTarih(tarih);
    }

    /** Kayıt sil (yoksa hata fırlatır) */
    @Transactional
    public void sil(Integer id) {
        if (!gunlukCalismaRepository.existsById(id)) {
            throw new IllegalArgumentException("Silinecek kayıt bulunamadı. id=" + id);
        }
        gunlukCalismaRepository.deleteById(id);
    }
}

