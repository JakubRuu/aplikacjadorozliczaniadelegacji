package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import javax.validation.constraints.*;
import java.util.Objects;

interface AddField {
}

interface UpdateField {
}

public class FieldDto {
    private String id;
    @NotBlank(groups = AddField.class)
    @Size(min = 2, max = 40, groups = {AddField.class, UpdateField.class})
    private String name;
    @Size(min = 2, max = 40, groups = {AddField.class, UpdateField.class})
    private String homeTeam;
    @Size(min = 2, max = 40, groups = {AddField.class, UpdateField.class})
    private String visitingTeam;
    @Min(value = 0, groups = {AddField.class, UpdateField.class})
    @Max(value = 10, groups = {AddField.class, UpdateField.class})
    private Integer fieldNo;
    @NotNull(groups = AddField.class)
    private String howManyReferees;
    @NotBlank
    private String person;

    public FieldDto() {
    }

    public FieldDto(String id, String name, String homeTeam, String visitingTeam, Integer fieldNo, String howManyReferees, String person) {
        this.id = id;
        this.name = name;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.fieldNo = fieldNo;
        this.howManyReferees = howManyReferees;
        this.person = person;
    }

    //todo


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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDto fieldDto = (FieldDto) o;
        return Objects.equals(id, fieldDto.id) && Objects.equals(name, fieldDto.name) && Objects.equals(homeTeam, fieldDto.homeTeam) && Objects.equals(visitingTeam, fieldDto.visitingTeam) && Objects.equals(fieldNo, fieldDto.fieldNo) && Objects.equals(howManyReferees, fieldDto.howManyReferees) && Objects.equals(person, fieldDto.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, homeTeam, visitingTeam, fieldNo, howManyReferees, person);
    }
}
