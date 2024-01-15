package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.reservation;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field.Field;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;


interface AddReservation {

}

interface UpdateReservation {

}

@Entity
public class Reservation {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @NotNull(groups = AddReservation.class)
    private LocalDateTime startDate;
    @NotNull(groups = AddReservation.class)
    private LocalDateTime endDate;

    @Size(min = 2, max = 20, groups = { AddReservation.class, UpdateReservation.class })
    private String reservationName;

    @ManyToOne
    private Field field;

    public Reservation() {
    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, String reservationName, Field field) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationName = reservationName;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(reservationName, that.reservationName) && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, reservationName, field);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reservationName='" + reservationName + '\'' +
                ", field=" + field +
                '}';
    }
}