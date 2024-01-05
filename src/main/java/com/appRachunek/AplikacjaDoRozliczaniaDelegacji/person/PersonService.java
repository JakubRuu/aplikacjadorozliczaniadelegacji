package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class PersonService {
    private final PersonRepository personRepository;


    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    List<Person> getAllFunction(@RequestParam(defaultValue = "ASC") SortType sortType) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.name()), "id");
        return personRepository.findAll(sort);
    }

    Person getFunction(String name) {
        return personRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("Brak funkcji!"));
    }


    Person addFunction(Person person) {
        personRepository.findByName(person.getName()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return personRepository.save(person);
    }

    Person deleteFunction(String name) {
        Person function = personRepository.findByName(name).orElseThrow(() -> new NoSuchElementException(""));
        personRepository.deleteById(function.getId());
        return function;
    }

    Person updateFunction(String name, Person person) {
        Person updatedFunction = personRepository
                .findByName(name)
                .orElseThrow(() -> new NoSuchElementException(""));

        if (person.getName() != null && !person.getName().equals(updatedFunction.getName())) {
            personRepository.findByName(person.getName())
                    .ifPresent(o -> {
                        throw new IllegalArgumentException("Dane się powtarzają");
                    });
            updatedFunction.setName(person.getName());
        }
        if (person.getFunction() != null) {
            updatedFunction.setFunction(person.getFunction());
        }
        return personRepository.save(updatedFunction);
    }
}
