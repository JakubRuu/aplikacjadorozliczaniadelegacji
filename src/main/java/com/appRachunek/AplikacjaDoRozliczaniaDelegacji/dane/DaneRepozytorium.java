package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.dane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaneRepozytorium extends JpaRepository<Dane, String> {
}
