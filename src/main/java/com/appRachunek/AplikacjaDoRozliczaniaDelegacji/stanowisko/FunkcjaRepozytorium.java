package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunkcjaRepozytorium extends JpaRepository<Funkcja, String> {
}
