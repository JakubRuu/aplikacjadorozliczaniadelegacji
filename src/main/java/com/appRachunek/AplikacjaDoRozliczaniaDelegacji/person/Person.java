package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;


import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String lastName;
    private String function;
    @OneToMany(mappedBy = "person")
    private List<Field> fields = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String function) {
        this.name = name;
        this.function = function;
    }

    public Person(Long id, String name, String function) {
        this.id = id;
        this.name = name;
        this.function = function;
    }

    public Person(Long id, String name, String lastName, String function) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.function = function;
    }

    public Person(Long id, String name, String lastName, String function, List<Field> fields) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.function = function;
        this.fields = fields;
    }

    public Person() {
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' + ", function='" + function + '\'' + ", fields=" + fields + '}';
    }

    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(lastName, person.lastName) && Objects.equals(function, person.function) && Objects.equals(fields, person.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, function, fields);
    }
}
