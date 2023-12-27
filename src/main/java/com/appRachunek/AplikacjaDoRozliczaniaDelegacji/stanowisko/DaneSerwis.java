package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class FunkcjaSerwis {
    private final FunkcjaRepozytorium funkcjaRepozytorium;


    public FunkcjaSerwis(FunkcjaRepozytorium funkcjaRepozytorium) {
        this.funkcjaRepozytorium = funkcjaRepozytorium;
    }

    List<Funkcja> pobracWszystkieDane() {
        return funkcjaRepozytorium.findAll();
    }

    Funkcja dodajDane(Funkcja funkcja) {
        funkcjaRepozytorium.findById(funkcja.getId()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return funkcjaRepozytorium.save(funkcja);
    }

    Funkcja usunDane(String id) {
        Funkcja dane = funkcjaRepozytorium.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        funkcjaRepozytorium.deleteById(id);
        return dane;
    }


    Funkcja zakutalizujDane(String id, Funkcja funkcja) {
        Funkcja zaktualizowaneDane = funkcjaRepozytorium
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));
        if (funkcja.getId() != null) {
            zaktualizowaneDane.setId(funkcja.getId());
        }
        if (funkcja.getFunkcja() != null) {
            zaktualizowaneDane.setFunkcja(funkcja.getFunkcja());
        }
        return funkcjaRepozytorium.save(zaktualizowaneDane);
    }
}
