package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
class FieldService {
    private final FieldRepository fieldRepository;
    private final PersonRepository personRepository;
    private final FieldUpdater fieldUpdater;
    private final FieldTransformer fieldTransformer;

    @Autowired
    public FieldService(FieldRepository fieldRepository, PersonRepository personRepository, FieldUpdater fieldUpdater, FieldTransformer fieldTransformer) {
        this.fieldRepository = fieldRepository;
        this.personRepository = personRepository;
        this.fieldUpdater = fieldUpdater;
        this.fieldTransformer = fieldTransformer;
    }


    List<FieldDto> getAllField() {
        return fieldRepository.findAll()
                .stream()
                .map(fieldTransformer::toDto)
                .collect(Collectors.toList());
    }

    FieldDto getFieldById(String id) {
        return fieldRepository.findById(id)
                .map(fieldTransformer::toDto)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("No field found!");
                });
    }


    List<FieldDto> getFieldBy(
            String identifier,
            String homeTeam,
            String fieldName,
            String visitingTeam,
            Integer fieldNo,
            boolean isAvailable,
            String howManyReferees) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues(); //matchingAll zawęża
        Example<Field> fieldExample = Example.of(
                new Field(identifier,  homeTeam, fieldName, visitingTeam, fieldNo, isAvailable,howManyReferees, new Person(fieldName))
                , exampleMatcher
        );
        return fieldRepository.findAll(fieldExample).stream()
                .map(fieldTransformer::toDto)
                .collect(Collectors.toList());
    }

    Field getField(String name) {
        return fieldRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("No field found!!"));
    }

    FieldDto addField(FieldDto fieldDto) {
        Field field = fieldTransformer.fromDto(fieldDto);
        Person personFromRepo = personRepository.findByName(field.getPerson().getName())
                .orElseThrow(NoSuchElementException::new);
        field.setPerson(personFromRepo);
        fieldRepository.findByNameAndPerson_Name(field.getName(),
                        field.getPerson().getName())
                .ifPresent(f -> {
                    throw new IllegalArgumentException("Field already exists!");
                });
        return fieldTransformer.toDto(fieldRepository.save(field));

    }

    FieldDto deleteField(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Field Found!"));
        fieldRepository.deleteById(id);
        return fieldTransformer.toDto(field);
    }

    FieldDto updateField(String id, FieldDto field) {
        return fieldTransformer.toDto(
                fieldUpdater.update(id, fieldTransformer.fromDto(field))
        );
    }
}

