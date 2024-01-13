package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.FieldDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

interface AddFunction {
}

interface UpdateFunction {
}

public class PersonDto {

    private Long id;
    @NotBlank(groups = AddFunction.class)
    @Size(min = 2, max = 20, groups = {AddFunction.class, UpdateFunction.class})
    private String name;
    private String lastName;
    private String function;
    private List<FieldDto> fields = new ArrayList<>();

    public PersonDto(Long id, String name, String lastName, String function, List<FieldDto> fields) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.function = function;
        this.fields = fields;
    }

    public PersonDto(Long id, String name, String function) {
        this.id = id;
        this.name = name;
        this.function = function;
    }

    public PersonDto(String name, String function) {
        this.name = name;
        this.function = function;
    }

    public PersonDto() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<FieldDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldDto> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto personDto = (PersonDto) o;
        return Objects.equals(id, personDto.id) && Objects.equals(name, personDto.name) && Objects.equals(lastName, personDto.lastName) && Objects.equals(function, personDto.function) && Objects.equals(fields, personDto.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, function, fields);
    }
}