package com.example.chargecracker.dao;

import com.example.chargecracker.dto.ReservationDto;
import com.example.chargecracker.model.Reservation;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> getAll();

    List<ReservationDto> getAllUser(Long id);

    Reservation getById(Long id);

    Boolean isReservationExist(Long id, String startTimestamp, String endTimestamp);

    void create(Reservation reservation);

    void delete(Long id);

    void update(Long id, Reservation reservation);
}
