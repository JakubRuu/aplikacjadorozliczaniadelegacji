package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import org.springframework.stereotype.Component;


@Component
public class FieldTransformer {

    public FieldDto toDto(Field field) {
        return new FieldDto(
                field.getId(),
                field.getName(),
                field.getHomeTeam(),
                field.getVisitingTeam(),
                field.getFieldNo(),
                field.getHowManyReferees(),
                field.getPerson().getName()
        );
    }

    public Field fromDto(FieldDto fieldDto) {
        return new Field(
                fieldDto.getId(),
                fieldDto.getName(),
                fieldDto.getHomeTeam(),
                fieldDto.getVisitingTeam(),
                fieldDto.getFieldNo(),
                fieldDto.getHowManyReferees(),
                new Person(fieldDto.getPerson())
        );
    }
}
