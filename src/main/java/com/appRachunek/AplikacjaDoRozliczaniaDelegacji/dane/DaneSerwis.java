package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.dane;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class DaneSerwis {
    private final DaneRepozytorium daneRepozytorium;


    public DaneSerwis(DaneRepozytorium daneRepozytorium) {
        this.daneRepozytorium = daneRepozytorium;
    }

    List<Dane> pobracWszystkieDane() {
        return daneRepozytorium.findAll();
    }

    Dane dodajDane(Dane dane) {
        daneRepozytorium.findById(dane.getImie()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return daneRepozytorium.save(dane);
    }

    Dane usunDane(String imie) {
        Dane dane = daneRepozytorium.findById(imie).orElseThrow(() -> new NoSuchElementException(""));
        daneRepozytorium.deleteById(imie);
        return dane;
    }


    Dane zakutalizujDane(String imie, Dane dane) {
        Dane zaktualizowaneDane = daneRepozytorium
                .findById(imie)
                .orElseThrow(() -> new NoSuchElementException(""));
        if (dane.getImie() != null) {
            zaktualizowaneDane.setImie(dane.getImie());
        }
        if (dane.getNazwisko() != null) {
            zaktualizowaneDane.setNazwisko(dane.getNazwisko());
        }
        if (dane.getFunkcja() != null) {
            zaktualizowaneDane.setFunkcja(dane.getFunkcja());
        }
        if (dane.getDataUrodzenia() != null) {
            zaktualizowaneDane.setDataUrodzenia(dane.getDataUrodzenia());
        }
        if (dane.getMiejsceUrodzenia() != null) {
            zaktualizowaneDane.setMiejsceUrodzenia(dane.getMiejsceUrodzenia());
        }
        if (dane.getMiejsceZamieszkania() != null) {
            zaktualizowaneDane.setMiejsceZamieszkania(dane.getMiejsceZamieszkania());
        }
        if (dane.getWojewodztwo() != null) {
            zaktualizowaneDane.setWojewodztwo(dane.getWojewodztwo());
        }
        if (dane.getPowiat() != null) {
            zaktualizowaneDane.setPowiat(dane.getPowiat());
        }
        if (dane.getGmina() != null) {
            zaktualizowaneDane.setGmina(dane.getGmina());
        }
        if (dane.getPesel() != null) {
            zaktualizowaneDane.setPesel(dane.getPesel());
        }
        if (dane.getUrzadSkarbowy() != null) {
            zaktualizowaneDane.setUrzadSkarbowy(dane.getUrzadSkarbowy());
        }

        return daneRepozytorium.save(zaktualizowaneDane);
    }
}
