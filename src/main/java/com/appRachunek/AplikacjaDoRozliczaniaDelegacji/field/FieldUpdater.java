package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
class FieldUpdater {
    private final FieldRepository fieldRepository;
    private final PersonRepository personRepository;

    public FieldUpdater(FieldRepository fieldRepository, PersonRepository personRepository) {
        this.fieldRepository = fieldRepository;
        this.personRepository = personRepository;
    }

    Field update(String id, Field field) {
        Field fieldToUpdate = fieldRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No field to update found!"));
        boolean isNameUpdated = updateNameOfObject(fieldToUpdate, field);
        updateIsAvailable(fieldToUpdate,field);
        updateHomeTeam(fieldToUpdate, field);
        updateVisitingTeam(fieldToUpdate, field);
        updateFieldNo(fieldToUpdate, field);
        updateHoManyReferees(fieldToUpdate, field);
        boolean isPersonUpdated = updatePerson(fieldToUpdate, field);
        checkIfFieldIsUnique(fieldToUpdate, isNameUpdated, isPersonUpdated);
        return fieldRepository.save(fieldToUpdate);

    }

    private void checkIfFieldIsUnique(Field fieldToUpdate, boolean isNameUpdated, boolean isPersonUpdated) {
        if (!isNameUpdated && !isPersonUpdated) {
            return;
        }
        fieldRepository.findByNameAndPerson_Name(
                        fieldToUpdate.getName(),
                        fieldToUpdate.getPerson().getName())
                .ifPresent(f -> {
                    throw new IllegalArgumentException("Field already exists!");
                });
    }

    private boolean updateNameOfObject(Field fieldToUpdate, Field field) {
        String nameOfObject = field.getName();
        if (nameOfObject != null) {
            fieldToUpdate.setName(nameOfObject);
        }
        return false;
    }

    private void updateHoManyReferees(Field fieldToUpdate, Field field) {
        String how = field.getHowManyReferees();
        if (how != null) {
            fieldToUpdate.setHowManyReferees(how);
        }
    }

    private void updateFieldNo(Field fieldToUpdate, Field field) {
        Integer fieldNo = field.getFieldNo();
        if (fieldNo != null) {
            fieldToUpdate.setFieldNo(fieldNo);
        }
    }

    private void updateHomeTeam(Field fieldToUpdate, Field field) {
        String homeTeam = field.getHomeTeam();
        if (homeTeam != null) {
            fieldToUpdate.setHomeTeam(homeTeam);
        }
    }
    private void updateIsAvailable(Field fieldToUpdate, Field field) {
        boolean isAvailable = field.isAvailable();
        fieldToUpdate.setAvailable(isAvailable);
    }

    private void updateVisitingTeam(Field fieldToUpdate, Field field) {
        String visitingTeam = field.getVisitingTeam();
        if (visitingTeam != null) {
            fieldToUpdate.setVisitingTeam(visitingTeam);
        }
    }

    private boolean updatePerson(Field fieldToUpdate, Field field) {
        Person person = field.getPerson();
        if (person != null) {
            Person personFromRepo = personRepository.findByName(person.getName())
                    .orElseThrow(() -> new NoSuchElementException("No person found!"));
            fieldToUpdate.setPerson(personFromRepo);
            return true;
        }
        return false;
    }
}
