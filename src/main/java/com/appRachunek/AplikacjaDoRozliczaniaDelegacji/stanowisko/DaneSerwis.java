package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class FunkcjaSerwis {
    private final FunkcjaRepozytorium funkcjaRepozytorium;


    public FunkcjaSerwis(FunkcjaRepozytorium funkcjaRepozytorium) {
        this.funkcjaRepozytorium = funkcjaRepozytorium;
    }

    List<Funkcja> pobracWszystkieDane(@RequestParam(defaultValue = "ASC") SortType sortType) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.name()), "id");
        return funkcjaRepozytorium.findAll(sort);
    }

    Funkcja pobranieFunkcjiPoId(String id) {
        return funkcjaRepozytorium.findById(id).orElseThrow(() -> new NoSuchElementException("Brak funkcji!"));
    }


    Funkcja dodajFunkcje(Funkcja funkcja) {
        funkcjaRepozytorium.findById(funkcja.getId()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return funkcjaRepozytorium.save(funkcja);
    }

    Funkcja usunFunkcje(String id) {
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
