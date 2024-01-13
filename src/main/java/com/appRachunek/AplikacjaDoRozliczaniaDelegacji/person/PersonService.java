package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
class PersonService {
    private final PersonRepository personRepository;
    private final PersonTransformer personTransformer;


    public PersonService(PersonRepository personRepository, PersonTransformer personTransformer) {
        this.personRepository = personRepository;
        this.personTransformer = personTransformer;
    }

    List<PersonDto> getAllFunctions(SortType sortType) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.name()), "name");
        return personRepository.findAll(sort).stream().map(personTransformer::toDto).collect(Collectors.toList());
    }

    PersonDto getFunction(String name) {
        return personRepository.findByName(name)
                .map(personTransformer::toDto)
                .orElseThrow(() -> new NoSuchElementException("Brak funkcji!"));
    }


    PersonDto addFunction(PersonDto personDto) {
        Person person=personTransformer.fromDto(personDto);
        personRepository.findByName(person.getName()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return personTransformer.toDto(personRepository.save(person));
    }

    PersonDto deleteFunction(String name) {
        Person person = personRepository.findByName(name).orElseThrow(() -> new NoSuchElementException(""));
        personRepository.deleteById(person.getId());
        return personTransformer.toDto(person);
    }

    PersonDto updateFunction(String name, PersonDto personDto) {
        Person person= personTransformer.fromDto(personDto);
        Person updatedFunction = personRepository
                .findByName(name)
                .orElseThrow(() -> new NoSuchElementException(""));

        if (person.getName() != null && !person.getName().equals(updatedFunction.getName())) {
            personRepository.findByName(person.getName())
                    .ifPresent(o -> {
                        throw new IllegalArgumentException("Person already exists!");
                    });
            updatedFunction.setName(person.getName());
        }
        if (person.getFunction() != null) {
            updatedFunction.setFunction(person.getFunction());
        }
        return personTransformer.toDto(personRepository.save(updatedFunction));
    }
}
