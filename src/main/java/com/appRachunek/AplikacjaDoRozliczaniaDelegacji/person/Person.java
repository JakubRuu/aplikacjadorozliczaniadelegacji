package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

interface AddFunction {
}

interface UpdateFunction {
}

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(groups = AddFunction.class)
    @Size(min = 2, max = 20, groups = {AddFunction.class, UpdateFunction.class})
    private String name;

    private String lastName;

    private String function;


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
        this.lastName=lastName;
        this.function = function;
    }

    public Person() {
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
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", function='" + function + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(lastName, person.lastName) && Objects.equals(function, person.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, function);
    }
}
