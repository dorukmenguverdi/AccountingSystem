package com.muhasebe.insaat.repositories;

import com.muhasebe.insaat.model.Calisan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalisanRepository extends JpaRepository<Calisan, Integer> {

    Optional<Calisan> findByAd(String ad);
}
