package com.muhasebe.insaat.repositories;

import com.muhasebe.insaat.model.Avans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvansRepository extends JpaRepository<Avans, Integer> {

    List<Avans> findByCalisanId(Integer calisanId);

    List<Avans> findByCalisanIdAndTarihBetween(Integer calisanId, LocalDate start, LocalDate end);
}
