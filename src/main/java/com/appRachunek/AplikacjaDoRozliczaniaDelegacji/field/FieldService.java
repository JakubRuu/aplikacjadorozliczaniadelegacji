package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class FieldService {
    private final FieldRepository fieldRepository;
    private final PersonRepository personRepository;
    private final FieldUpdater fieldUpdater;

    @Autowired
    public FieldService(FieldRepository fieldRepository, PersonRepository personRepository, FieldUpdater fieldUpdater) {
        this.fieldRepository = fieldRepository;
        this.personRepository = personRepository;
        this.fieldUpdater = fieldUpdater;
    }

    List<Field> getAllField(@RequestParam(defaultValue = "ASC") SortType sortType) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.name()), "id");
        return fieldRepository.findAll(sort);
    }

    Field getField(String name) {
        return fieldRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("No field found!!"));
    }

    Field addField(Field field) {
        String fieldName = field.getPerson().getName();
        Person personFromRepo = personRepository.findByName(fieldName)
                .orElseThrow(() -> {
                    throw new NoSuchElementException();
                });
        field.setPerson(personFromRepo);
        fieldRepository.findByNameAndPerson_Name(field.getName(),
                        field.getPerson().getName())
                .ifPresent(f -> {
                    throw new IllegalArgumentException("Field already exists!");
                });
        return fieldRepository.save(field);

    }

    Field deleteField(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Field Found!"));
        fieldRepository.deleteById(id);
        return field;
    }

    Field updateField(String id, Field field) {
        return fieldUpdater.update(id, field);
    }
}

