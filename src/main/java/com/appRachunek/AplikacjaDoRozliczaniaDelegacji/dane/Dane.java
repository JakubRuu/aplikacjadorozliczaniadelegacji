package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.dane;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

interface DodajDane {
}

interface ZaktualizujDane {
}

@Entity
public class Dane {

    @Id
    @Size(min = 3, max = 20)
    @NotBlank
    private String imie;
    private String nazwisko;
    private String funkcja;
    private String dataUrodzenia;
    private String miejsceUrodzenia;
    private String miejsceZamieszkania;
    private String wojewodztwo;
    private String powiat;
    private String gmina;
    private String urzadSkarbowy;
    private String pesel;

    public Dane() {
    }

    public Dane(String funkcja, String nazwisko, String imie, String dataUrodzenia, String miejsceUrodzenia, String miejsceZamieszkania, String wojewodztwo, String powiat, String gmina, String urzadSkarbowy, String pesel) {
        this.funkcja = funkcja;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.dataUrodzenia = dataUrodzenia;
        this.miejsceUrodzenia = miejsceUrodzenia;
        this.miejsceZamieszkania = miejsceZamieszkania;
        this.wojewodztwo = wojewodztwo;
        this.powiat = powiat;
        this.gmina = gmina;
        this.urzadSkarbowy = urzadSkarbowy;
        this.pesel = pesel;
    }

    public String getFunkcja() {
        return funkcja;
    }

    public void setFunkcja(String funkcja) {
        this.funkcja = funkcja;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getMiejsceUrodzenia() {
        return miejsceUrodzenia;
    }

    public void setMiejsceUrodzenia(String miejsceUrodzenia) {
        this.miejsceUrodzenia = miejsceUrodzenia;
    }

    public String getMiejsceZamieszkania() {
        return miejsceZamieszkania;
    }

    public void setMiejsceZamieszkania(String miejsceZamieszkania) {
        this.miejsceZamieszkania = miejsceZamieszkania;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getPowiat() {
        return powiat;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getUrzadSkarbowy() {
        return urzadSkarbowy;
    }

    public void setUrzadSkarbowy(String urzadSkarbowy) {
        this.urzadSkarbowy = urzadSkarbowy;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = this.pesel;
    }
}
