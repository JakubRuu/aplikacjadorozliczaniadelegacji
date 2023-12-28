package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.FunkcjaArgumentyProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.PobierzIdFunkcjeArgProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FunkcjaRepozytoriumTest {
    @Autowired
    FunkcjaRepozytorium funkcjaRepozytorium;
    @Autowired
    TestEntityManager testEntityManager;

    @ParameterizedTest
    @ArgumentsSource(FunkcjaArgumentyProvider.class)
    void kiedy_lista_argumentow_jest_dostepna_w_testach_repo_wtedy_metoda_wyszukaj_wszystko_powinna_zwrocic_argument_1_listy(List<Funkcja> arg0,
                                                                                                                             List<Funkcja> arg1) {
        //given
        arg0.forEach(o ->
                testEntityManager.persist(o));
        //when
        List<Funkcja> result = funkcjaRepozytorium.findAll();
        //then
        assertEquals(arg1, result);
    }

    @ParameterizedTest
    @ArgumentsSource(PobierzIdFunkcjeArgProvider.class)
    void when_find_by_arg_1_when_arg_0_is_available_then_arg_2_item_should_be_returned(List<Funkcja> arg0,
                                                                                       String arg1,
                                                                                       Optional<Funkcja> arg2) {
        //given
        arg0.forEach(o -> testEntityManager.persist(o));
        //when
        Optional<Funkcja> result = funkcjaRepozytorium.findById(arg1);
        //then
        assertEquals(arg2, result);

    }

    @Test
    void kiedy_zapisany_arg_0_do_repo_wtedy_powinien_byc_on_zapisany() {

        //given
        Funkcja arg0 = new Funkcja("1", "Sędzia");
        //when
        funkcjaRepozytorium.save(arg0);

        //then
        assertEquals(arg0, testEntityManager.find(Funkcja.class, "1"));
    }

    @Test
    void kiedy_zapisany_arg_0_ma_zly_primary_key_wtedy_powinien_pokazac_sie_blad() {

        //given
        Funkcja arg0 = new Funkcja(null, "Sędzia");
        //when
        //then
        assertThrows(JpaSystemException.class, () -> {
            funkcjaRepozytorium.save(arg0);
        });

    }

}