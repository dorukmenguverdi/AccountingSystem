package com.muhasebe.insaat.services;

import com.muhasebe.insaat.model.Calisan;
import com.muhasebe.insaat.repositories.CalisanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalisanService {

    private final CalisanRepository calisanRepository;

    public CalisanService(CalisanRepository calisanRepository) {
        this.calisanRepository = calisanRepository;
    }

    /** Kaydet / güncelle (id null ise yeni kayıt, dolu ise update) */
    @Transactional
    public Calisan kaydet(Calisan calisan) {
        return calisanRepository.save(calisan);
    }

    /** ID ile tek çalışan getir (yoksa hata fırlatır) */
    @Transactional(readOnly = true)
    public Calisan idIleBul(Integer id) {
        return calisanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Çalışan bulunamadı. id=" + id));
    }

    /** Tüm çalışanları getir */
    @Transactional(readOnly = true)
    public List<Calisan> tumunuGetir() {
        return calisanRepository.findAll();
    }

    /** Çalışan sil (yoksa hata fırlatır) */
    @Transactional
    public void sil(Integer id) {
        if (!calisanRepository.existsById(id)) {
            throw new IllegalArgumentException("Silinecek çalışan bulunamadı. id=" + id);
        }
        calisanRepository.deleteById(id);
    }
}