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
class FunkcjaSerwisTest {

    @MockBean
    FunkcjaRepozytorium funkcjaRepozytorium;
    @Autowired
    FunkcjaSerwis funkcjaSerwis;

    @ParameterizedTest
    @ArgumentsSource(SortFunctionArgumentProvider.class)
    void when_get_all_with_arg_0_order_then_arg_1_order_br_provided_to_repo( SortType arg0, Sort arg1){
        //given
        ArgumentCaptor<Sort> sortArgumentCaptor= ArgumentCaptor.forClass(Sort.class);
        //when
        funkcjaSerwis.getAllF(arg0);
        //then
        Mockito.verify(funkcjaRepozytorium).findAll(sortArgumentCaptor.capture());
        assertEquals(arg1,sortArgumentCaptor.getValue());
    }


    @Test
    void when_add_invalid_function_then_exception_should_be_thrown() {
        //given
        String id = "1";
        Funkcja arg = new Funkcja(id, "Sędzia");
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.of(arg));

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            funkcjaSerwis.addF(arg);
        });
    }

    @Test
    void when_add_new_function_then_ir_should_be_added_to_repo() {
        //given
        String id = "1";
        Funkcja arg = new Funkcja(id, "Sędzia");
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.empty());
        Mockito.when(funkcjaRepozytorium.save(arg)).thenReturn(arg);
        //when
        Funkcja result = funkcjaSerwis.addF(arg);
        //then
        assertEquals(arg, result);
        Mockito.verify(funkcjaRepozytorium).save(arg);
    }

    @Test
    void when_delete_existing_function_then_id_should_be_removed_from_the_repo() {
        //given
        String id = "1";
        Funkcja arg = new Funkcja(id, "Sędzia");
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.of(arg));

        //when
        Funkcja result = funkcjaSerwis.deleteF(id);
        //then
        assertEquals(arg, result);
        Mockito.verify(funkcjaRepozytorium).deleteById(id);
    }

    @Test
    void when_delete_non_existing_function_then_exception_should_be_thrown() {
        //given
        String id = "1";

        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            funkcjaSerwis.deleteF(id);
        });
    }

    @Test
    void when_update_non_existing_id_then_exception_should_be_thrown() {
        //given
        String id = "1";
        Funkcja arg = new Funkcja(id, "Sędzia");
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            funkcjaSerwis.updateF(id, arg);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateFunctionArgumentProvider.class)
    void when_update_arg_1_function_with_arg2_date_then_function_should_be_updated_to_arg3(
            String id,
            Funkcja arg1,
            Funkcja arg2,
            Funkcja arg3
    ) {
        //given
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.of(arg1));
        Mockito.when(funkcjaRepozytorium.save(arg1)).thenReturn(arg3);
        //when
        Funkcja result = funkcjaSerwis.updateF(id, arg2);
        //then
        assertEquals(arg3, result);
        Mockito.verify(funkcjaRepozytorium).save(arg3);
    }

    @Test
    void when_get_non_existing_function_then_exception_should_be_thrown() {
        //given
        String id = "1";

        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            funkcjaSerwis.getFWithId(id);
        });
    }

    @Test
    void when_get_existing_function_then_function_should_be_returned() {
        //given
        String id = "1";
        Funkcja arg = new Funkcja(id, "Sędzia");
        Mockito.when(funkcjaRepozytorium.findById(id)).thenReturn(Optional.of(arg));

//when
        Funkcja result = funkcjaSerwis.getFWithId(id);
//then
        assertEquals(arg, result);
        Mockito.verify(funkcjaRepozytorium).findById(id);

    }

    @TestConfiguration
    static class FunkcjeSerwisTestConfig {
        @Bean
        FunkcjaSerwis funkcjaSerwis(FunkcjaRepozytorium funkcjaRepozytorium) {
            return new FunkcjaSerwis(funkcjaRepozytorium);
        }
    }
}
