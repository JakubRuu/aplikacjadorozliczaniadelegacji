package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;
import org.springframework.stereotype.Component;

@Component
public class ReservationTransformer {

    ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setReservationName(reservation.getReservationName());
        reservationDto.setStartDate(reservationDto.getStartDate());
        reservationDto.setEndDate(reservationDto.getEndDate());
        reservationDto.setFieldId(reservation.getField().getId());
        return reservationDto;
    }
    Reservation fromDto(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationName(reservationDto.getReservationName());
        reservation.setField(new Field(reservation.getId()));
        return reservation;
    }
}
