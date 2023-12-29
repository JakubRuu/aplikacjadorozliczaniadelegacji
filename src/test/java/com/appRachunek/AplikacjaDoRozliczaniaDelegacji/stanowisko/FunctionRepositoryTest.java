package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.GetAllFunctionArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.GetByIdFunctionArgumentProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class FunctionRepositoryTest {
    @Autowired
    FunctionRepository functionRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @AfterEach
    void tearDown() {
        testEntityManager.clear();
    }

    @ParameterizedTest
    @ArgumentsSource(GetAllFunctionArgumentProvider.class)
    void when_arg_0_data_are_available_in_repo_then_find_all_should_return_arg_1_list(List<Function> arg0,
                                                                                      List<Function> arg1) {
        //given
        arg0.forEach(o ->
                testEntityManager.persist(o));
        //when
        List<Function> result = functionRepository.findAll();
        //then
        assertEquals(arg1, result);
    }

    @ParameterizedTest
    @ArgumentsSource(GetByIdFunctionArgumentProvider.class)
    void when_find_by_arg_1_when_arg_0_is_available_then_arg_2_item_should_be_returned(List<Function> arg0,
                                                                                       Long arg1,
                                                                                       Optional<Function> arg2) {

        //given
        arg0.forEach(o -> {
            testEntityManager.persist(o);
        });
        //when
        Optional<Function> result = functionRepository.findById(arg1);
        //then
        assertEquals(arg2, result);

    }

    @Test
    void when_save_arg_0_to_repo_then_it_should_be_stored_properly() {

        //given
        Function arg0 = new Function(1L, "Rutkowski", "Sędzia");
        //when
        functionRepository.save(arg0);

        //then
        assertEquals(arg0, testEntityManager.find(Function.class, 1L));
    }

}