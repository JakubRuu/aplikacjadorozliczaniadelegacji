package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.FieldRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final int MIN_DURATION_OF_THE_MEETING = 15;
    private final ReservationRepository reservationRepository;
    private final FieldRepository fieldRepository;
    private final ReservationTransformer reservationTransformer;

    public ReservationService(ReservationRepository reservationRepository, FieldRepository fieldRepository, ReservationTransformer reservationTransformer) {
        this.reservationRepository = reservationRepository;
        this.fieldRepository = fieldRepository;
        this.reservationTransformer = reservationTransformer;
    }

    ReservationDto addReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationTransformer.fromDto(reservationDto);
        Field field = fieldRepository.findById(reservation.getField().getId())
                .orElseThrow(() -> new NoSuchElementException("Can't find field"));
        reservation.setField(field);
        validateField(field);
        validateReservationDuration(reservation);
        validateReservationTime(field, reservation);
        return reservationTransformer.toDto(reservationRepository.save(reservation));
    }

    private void validateReservationTime(Field field, Reservation reservation) {
        reservationRepository.findByField_IdAndStartDateLessThanAndEndDateGreaterThan(
                field.getId(),
                reservation.getEndDate(),
                reservation.getStartDate()
        ).ifPresent(r -> {
            throw new IllegalArgumentException("Reservation during provided time already exits");
        });
    }

    private void validateReservationDuration(Reservation reservation) {
        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            throw new IllegalArgumentException("end date is before start date!");
        }
        if (ChronoUnit.MINUTES.between(reservation.getStartDate(), reservation.getEndDate()) < MIN_DURATION_OF_THE_MEETING) {
            throw new IllegalArgumentException("meeting can't be shorter than " + MIN_DURATION_OF_THE_MEETING + " min!");
        }
    }

    private void validateField(Field field) {
        if (!field.isAvailable()) {
            throw new IllegalArgumentException("Field is not available");
        }
    }

    ReservationDto updateReservation(String id, ReservationDto reservationDto) {
        Reservation reservation = reservationTransformer.fromDto(reservationDto);
        Reservation reservationFromDb = reservationRepository.getReferenceById(id);
        updateReservationName(reservation, reservationFromDb);
        updateFieldReservation(reservation, reservationFromDb);
        updateReservationStartAndEndData(reservation,reservationFromDb);
     return reservationTransformer.toDto(  reservationRepository.save(reservationFromDb));
    }

    private void updateReservationStartAndEndData(Reservation reservation, Reservation reservationFromDb) {
        LocalDateTime startDate = reservation.getStartDate();
        LocalDateTime endDate = reservation.getEndDate();
        validateReservationDuration(reservation);
        boolean isChange=false;
        if (startDate != null) {
            isChange=true;
            reservationFromDb.setStartDate(startDate);
        }
        if (endDate != null) {
            isChange=true;
            reservationFromDb.setEndDate(endDate);
        }
        //todo naprawa błedu aktualizacji
        if (isChange) {
            validateReservationTime(reservationFromDb.getField(),reservationFromDb);
        }
    }

    private void updateReservationName(Reservation reservation, Reservation reservationFromDb) {
        String newReservationName = reservation.getReservationName();
        if (reservation.getReservationName() != null) {
            reservationFromDb.setReservationName(newReservationName);
        }
    }

    private void updateFieldReservation(Reservation reservation, Reservation reservationFromDb) {
        String fieldId = reservation.getField().getId();
        if (fieldId != null) {
            Field field = fieldRepository.findById(fieldId)
                    .orElseThrow(() -> new NoSuchElementException("Can't find field!"));
            validateField(field);
            reservationFromDb.setField(field);
        }
    }

}
