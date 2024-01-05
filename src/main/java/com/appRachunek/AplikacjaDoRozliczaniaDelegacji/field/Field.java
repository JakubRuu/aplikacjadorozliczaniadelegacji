package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private Person person;

    public Field() {
    }

    public Field(String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, Person person) {
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
        this.person = person;
    }

    public Field(String id, String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, Person person) {
        this.id = id;
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return fieldNo == field.fieldNo && Objects.equals(id, field.id) && Objects.equals(name, field.name) && Objects.equals(homeTeam, field.homeTeam) && Objects.equals(visitingTeam, field.visitingTeam) && Objects.equals(howManyReferees, field.howManyReferees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, homeTeam, visitingTeam, fieldNo, howManyReferees);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", nameOfObject='" + name + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", visitingTeam='" + visitingTeam + '\'' +
                ", fieldNo=" + fieldNo +
                ", noRef=" + howManyReferees +
                '}';
    }

}
