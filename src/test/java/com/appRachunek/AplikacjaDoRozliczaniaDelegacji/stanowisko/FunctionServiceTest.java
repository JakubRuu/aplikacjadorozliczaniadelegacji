package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.SortFunctionArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.UpdateFunctionArgumentProvider;
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
class FunctionServiceTest {

    @MockBean
    FunctionRepository functionRepository;
    @Autowired
    FunctionService functionService;

    @ParameterizedTest
    @ArgumentsSource(SortFunctionArgumentProvider.class)
    void when_get_all_with_arg_0_order_then_arg_1_order_br_provided_to_repo(SortType arg0, Sort arg1) {
        //given
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        //when
        functionService.getAllFunction(arg0);
        //then
        Mockito.verify(functionRepository).findAll(sortArgumentCaptor.capture());
        assertEquals(arg1, sortArgumentCaptor.getValue());
    }


    @Test
    void when_add_invalid_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";
        Function arg = new Function(name, "Sędzia");
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.of(arg));

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> functionService.addFunction(arg));
    }

    @Test
    void when_add_new_function_then_ir_should_be_added_to_repo() {
        //given
        String name = "Rutkowski";
        Function arg = new Function(name, "Sędzia");
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.empty());
        Mockito.when(functionRepository.save(arg)).thenReturn(arg);
        //when
        Function result = functionService.addFunction(arg);
        //then
        assertEquals(arg, result);
        Mockito.verify(functionRepository).save(arg);
    }

    @Test
    void when_delete_existing_function_then_id_should_be_removed_from_the_repo() {
        //given
        String name = "Rutkowski";
        Long id = 1L;
        Function arg = new Function(id, name, "Sędzia");
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.of(arg));

        //when
        Function result = functionService.deleteFunction(name);
        //then
        assertEquals(arg, result);
        Mockito.verify(functionRepository).deleteById(id);
    }

    @Test
    void when_delete_non_existing_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";

        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> functionService.deleteFunction(name));
    }

    @Test
    void when_update_non_existing_id_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";
        Function arg = new Function(name, "Sędzia");
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> functionService.updateFunction(name, arg));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateFunctionArgumentProvider.class)
    void when_update_arg_1_function_with_arg2_date_then_function_should_be_updated_to_arg3(
            String name,
            Function arg1,
            Function arg2,
            Function arg3
    ) {
        //given
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.of(arg1));
        Mockito.when(functionRepository.save(arg1)).thenReturn(arg3);
        //when
        Function result = functionService.updateFunction(name, arg2);
        //then
        assertEquals(arg3, result);
        Mockito.verify(functionRepository).save(arg3);
    }

    @Test
    void when_get_non_existing_function_then_exception_should_be_thrown() {
        //given
        String name = "Rutkowski";

        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> functionService.getFunction(name));
    }

    @Test
    void when_get_existing_function_then_function_should_be_returned() {
        //given
        String name = "Rutkowski";
        Function arg = new Function(name, "Sędzia");
        Mockito.when(functionRepository.findByName(name)).thenReturn(Optional.of(arg));

//when
        Function result = functionService.getFunction(name);
//then
        assertEquals(arg, result);
        Mockito.verify(functionRepository).findByName(name);

    }
    @Test
    void when_update_function_name_which_is_not_unique_then_exception_should_be_thrown(){
        //given
        String name1= "Rutkowski";
        Function existingFun1 = new Function(name1,"Sędzia");
        String name2= "Iksiński";
        Function existingFun2 = new Function(name2,"Sędzia Asystent");
        Function updateFunction= new Function(name2,"Sędzia");
        Mockito.when(functionRepository.findByName(name1)).thenReturn(Optional.of(existingFun1));
        Mockito.when(functionRepository.findByName(name2)).thenReturn(Optional.of(existingFun2));

        //when
        //then
        assertThrows(IllegalArgumentException.class, ()->{
            functionService.updateFunction(name1, updateFunction);
        });
        Mockito.verify(functionRepository,Mockito.never()).save(updateFunction);
    }

    @TestConfiguration
    static class FunctionServiceTestConfig {
        @Bean
        FunctionService functionService(FunctionRepository functionRepository) {
            return new FunctionService(functionRepository);
        }
    }
}
