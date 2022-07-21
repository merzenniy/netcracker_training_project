package com.example.chargecracker.service;

import com.example.chargecracker.dto.ReservationDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getReservations();

    List<ReservationDto> getUserReservations(Long id);

    Reservation getReservation(Long id);

    void createReservation(Reservation reservation) throws ModelAttributeException;

    void createReservationUser(Reservation reservation) throws ModelAttributeException, ModelAlreadyExistsException;

    void deleteReservation(Long id) throws ModelNotFoundException;

    void updateReservation(Long id, Reservation reservation) throws ModelNotFoundException, ModelAttributeException;
}
