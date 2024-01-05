package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args.SortFunctionArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args.UpdateFunctionArgumentProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @MockBean
    PersonRepository personRepository;
    @MockBean
    PersonTransformer personTransformer;
    @Autowired
    PersonService personService;

    @ParameterizedTest
    @ArgumentsSource(SortFunctionArgumentProvider.class)
    void when_get_all_with_arg_0_order_then_arg_1_order_br_provided_to_repo(SortType arg0, Sort arg1) {
        //given
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        //when
        personService.getAllFunction(arg0);
        //then
        Mockito.verify(personRepository).findAll(sortArgumentCaptor.capture());
        assertEquals(arg1, sortArgumentCaptor.getValue());
    }


    @Test
    void when_add_invalid_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";
        Person arg = new Person(name, "Sędzia");
        PersonDto argDto = new PersonDto(name, "Sędzia");
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.of(arg));
        Mockito.when(personTransformer.fromDto(argDto)).thenReturn(arg);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> personService.addFunction(argDto));
    }

    @Test
    void when_add_new_function_then_it_should_be_added_to_repo() {
        //given
        String name = "Rutkowski";
        Person arg = new Person(name, "Sędzia");
        PersonDto argDto = new PersonDto(name, "Sędzia");
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.empty());
        Mockito.when(personRepository.save(arg)).thenReturn(arg);
        Mockito.when(personTransformer.fromDto(argDto)).thenReturn(arg);
        Mockito.when(personTransformer.toDto(arg)).thenReturn(argDto);
        //when
        PersonDto result = personService.addFunction(argDto);
        //then
        assertEquals(argDto, result);
        Mockito.verify(personRepository).save(arg);
    }

    @Test
    void when_delete_existing_function_then_id_should_be_removed_from_the_repo() {
        //given
        String name = "Rutkowski";
        Long id = 1L;
        Person arg = new Person(id, name, "Sędzia");
        PersonDto argDto = new PersonDto(id, name, "Sędzia");
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.of(arg));
        Mockito.when(personTransformer.toDto(arg)).thenReturn(argDto);

        //when
        PersonDto result = personService.deleteFunction(name);
        //then
        assertEquals(argDto, result);
        Mockito.verify(personRepository).deleteById(id);
    }

    @Test
    void when_delete_non_existing_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";

        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> personService.deleteFunction(name));
    }

    @Test
    void when_update_non_existing_id_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";
        PersonDto argDto = new PersonDto(name, "Sędzia");
        Person arg = new Person(name, "Sędzia");
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.empty());
        Mockito.when(personTransformer.fromDto(argDto)).thenReturn(arg);

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> personService.updateFunction(name, argDto));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateFunctionArgumentProvider.class)
    void when_update_arg_1_function_with_arg2_date_then_function_should_be_updated_to_arg3(
            String name,
            Person arg1,
            PersonDto arg2,
            Person arg3,
            Person arg4,
            PersonDto arg5
    ) {
        //given
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.of(arg1));
        Mockito.when(personRepository.save(arg1)).thenReturn(arg4);
        Mockito.when(personTransformer.toDto(arg4)).thenReturn(arg5);
        Mockito.when(personTransformer.fromDto(arg2)).thenReturn(arg3);
        //when
        PersonDto result = personService.updateFunction(name, arg2);
        //then
        assertEquals(arg5, result);
        Mockito.verify(personRepository).save(arg4);
    }

    @Test
    void when_get_non_existing_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";

        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> personService.getFunction(name));
    }

    @Test
    void when_get_existing_function_then_function_should_be_returned() {
        //given
        String name = "Rutkowski";
        Person arg = new Person(name, "Sędzia");
        PersonDto argDto = new PersonDto(name, "Sędzia");
        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.of(arg));
        Mockito.when(personTransformer.toDto(arg)).thenReturn(argDto);

//when
        PersonDto result = personService.getFunction(name);
//then
        assertEquals(argDto, result);
        Mockito.verify(personRepository).findByName(name);

    }

    @Test
    void when_update_function_name_which_is_not_unique_then_exception_should_be_thrown() {
        //given
        String name1 = "Rutkowski";
        Person existingFun1 = new Person(name1, "Sędzia");
        String name2 = "Iksiński";
        Person existingFun2 = new Person(name2, "Sędzia Asystent");
        PersonDto updateFunctionDto = new PersonDto(name2, "Sędzia");
        Person updateFunction = new Person(name2, "Sędzia");
        Mockito.when(personRepository.findByName(name1)).thenReturn(Optional.of(existingFun1));
        Mockito.when(personRepository.findByName(name2)).thenReturn(Optional.of(existingFun2));
        Mockito.when(personTransformer.fromDto(updateFunctionDto)).thenReturn(updateFunction);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            personService.updateFunction(name1, updateFunctionDto);
        });
        Mockito.verify(personRepository, Mockito.never()).save(updateFunction);
    }

    @TestConfiguration
    static class FunctionServiceTestConfig {
        @Bean
        PersonService functionService(PersonRepository personRepository, PersonTransformer personTransformer) {
            return new PersonService(personRepository, personTransformer);
        }
    }
}
