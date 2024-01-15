package com.appRachunek.AplikacjaDoRozliczaniaDelegacji;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.FieldRepository;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.PersonRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class AppInitializer {
    private final FieldRepository fieldRepository;
    private final PersonRepository personRepository;

    public AppInitializer(FieldRepository fieldRepository, PersonRepository personRepository) {
        this.fieldRepository = fieldRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    void init(){
        Person person1= new Person("PZPN", "Sędzia");
        Person person2= new Person("PZPN1", "Sędzia1");
        Person person3= new Person("PZPN2", "Sędzia2");

        Field field1= new Field("LegiaCentral1","Legia1","Jaga1",1,"1", false,person1);
        Field field2 = new Field("LegiaCentral2","Legia2","Jaga2",2,"3", true,person1);
        Field field3 = new Field("LegiaCentral3","Legia3","Jaga3",3,"4", false,person1);

        Field field4 = new Field("LegiaCentral3","Legia4","Jaga4",3,"42", false,person2);
        Field field5 = new Field("LegiaCentral2","Legia5","Jaga5",1,"41", true,person2);
        Field field6 = new Field("LegiaCentral1","Legia6","Jaga6",4,"4", false,person2);

        Field field7 = new Field("LegiaCentral3","Legia7","Jaga7",5,"45", false,person3);
        Field field8 = new Field("LegiaCentral2","Legia8","Jaga8",6,"45", false,person3);
        Field field9 = new Field("LegiaCentral1","Legia9","Jaga9",2,"45", true, person2);


        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        fieldRepository.save(field1);
        fieldRepository.save(field2);
        fieldRepository.save(field3);
        fieldRepository.save(field4);
        fieldRepository.save(field5);
        fieldRepository.save(field6);
        fieldRepository.save(field7);
        fieldRepository.save(field8);
        fieldRepository.save(field9);
    }
}
