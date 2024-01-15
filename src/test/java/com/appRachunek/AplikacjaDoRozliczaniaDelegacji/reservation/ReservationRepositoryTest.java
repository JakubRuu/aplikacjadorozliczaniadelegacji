package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation.args.FindReservationByDateArgumentProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationRepositoryTest {


    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @ParameterizedTest
    @ArgumentsSource(FindReservationByDateArgumentProvider.class)
    void when_check_if_reservation_with_start_date_and_end_date_then_result_should_be_provided(LocalDateTime startDate,
                                                                                               LocalDateTime endDate,
                                                                                               boolean exists) {
        //given
        Field field = new Field(
                "Test",
                "Legia",
                "Jaga",
                5,
                "10",
                null);
       Field fieldFromDb= testEntityManager.persist(field);
        Reservation reservation = new Reservation(
                LocalDateTime.of(2022, 8, 20, 10, 0, 0),
                LocalDateTime.of(2022, 8, 20, 11, 0, 0),
                "test",
                fieldFromDb );
        testEntityManager.persist(reservation);

        //when
        Optional<Reservation> result = reservationRepository.findByField_IdAndStartDateLessThanAndEndDateGreaterThan(
fieldFromDb.getId(),
                endDate,
                startDate
        );

        //then
        Assertions.assertEquals(exists,result.isPresent());

    }

}
