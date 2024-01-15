package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Optional<Reservation> findByField_IdAndStartDateLessThanAndEndDateGreaterThan(
            String fieldsId,
            LocalDateTime endDate,
            LocalDateTime startDate
    );
}