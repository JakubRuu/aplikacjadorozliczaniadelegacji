package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.FieldTransformer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class PersonTransformer {
    private final FieldTransformer fieldTransformer;

    public PersonTransformer(FieldTransformer fieldTransformer) {
        this.fieldTransformer = fieldTransformer;
    }

    PersonDto toDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getLastName(),
                person.getFunction(),
                person.getFields().stream().map(fieldTransformer::toDto).collect(Collectors.toList())
        );
    }

    Person fromDto(PersonDto personDto){
        return new Person(
                personDto.getId(),
                personDto.getName(),
                personDto.getLastName(),
                personDto.getFunction(),
                personDto.getFields()
                        .stream()
                        .map(fieldTransformer::fromDto)
                         .collect(Collectors.toList())
        );
    }
}
