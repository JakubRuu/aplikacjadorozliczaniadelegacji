package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation.Reservation;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class Field {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;
    private String homeTeam;
    private String visitingTeam;
    private Integer fieldNo;
    private String howManyReferees;
    private boolean isAvailable;
    @ManyToOne
    private Person person;
    //Todo
    @OneToMany(mappedBy = "field")
    private List<Reservation> reservations;

    public Field() {
    }

    public Field(String id, String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, boolean isAvailable, Person person, List<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
        this.isAvailable = isAvailable;
        this.person = person;
        this.reservations = reservations;
    }

    public Field(String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, boolean isAvailable, Person person, List<Reservation> reservations) {
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
        this.isAvailable = isAvailable;
        this.person = person;
        this.reservations = reservations;
    }

    public Field(String id, String name, String homeTeam, String visitingTeam, Integer fieldNo,boolean isAvailable, String howManyReferees, Person person) {
        this.id = id;
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.isAvailable=isAvailable;
        this.howManyReferees = howManyReferees;
        this.person = person;
    }

    public Field(String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, boolean isAvailable, Person person) {
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
        this.isAvailable = isAvailable;
        this.person = person;
    }

    public Field(String id, String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees) {
        this.id = id;
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
    }

    public Field(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public Integer getFieldNo() {
        return fieldNo;
    }

    public void setFieldNo(Integer fieldNo) {
        this.fieldNo = fieldNo;
    }

    public String getHowManyReferees() {
        return howManyReferees;
    }

    public void setHowManyReferees(String howManyReferees) {
        this.howManyReferees = howManyReferees;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return isAvailable == field.isAvailable && Objects.equals(id, field.id) && Objects.equals(name, field.name) && Objects.equals(homeTeam, field.homeTeam) && Objects.equals(visitingTeam, field.visitingTeam) && Objects.equals(fieldNo, field.fieldNo) && Objects.equals(howManyReferees, field.howManyReferees) && Objects.equals(person, field.person) && Objects.equals(reservations, field.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, homeTeam, visitingTeam, fieldNo, howManyReferees, isAvailable, person, reservations);
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", visitingTeam='" + visitingTeam + '\'' +
                ", fieldNo=" + fieldNo +
                ", howManyReferees='" + howManyReferees + '\'' +
                ", isAvailable=" + isAvailable +
                ", person=" + person +
                ", reservations=" + reservations +
                '}';
    }
}


