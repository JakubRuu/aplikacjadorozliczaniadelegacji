package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

interface DodajFunkcje {
}

interface ZaktualizujFunkcje {
}

@Entity
public class Funkcja {

    @Id
    @Size(min = 1, max = 20)
    @NotBlank
    private String id;
    private String funkcja;

    public Funkcja(String id, String funkcja) {
        this.id = id;
        this.funkcja = funkcja;
    }

    public Funkcja() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunkcja() {
        return funkcja;
    }

    public void setFunkcja(String funkcja) {
        this.funkcja = funkcja;
    }

    @Override
    public String toString() {
        return "Dane{" +
                "id='" + id + '\'' +
                ", funkcja='" + funkcja + '\'' +
                '}';
    }
}
