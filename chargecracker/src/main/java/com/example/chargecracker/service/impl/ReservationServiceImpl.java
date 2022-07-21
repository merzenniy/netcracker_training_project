package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.ReservationDAO;
import com.example.chargecracker.dao.StationDAO;
import com.example.chargecracker.dto.ReservationDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Reservation;
import com.example.chargecracker.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private StationDAO stationDAO;

    @Override
    public List<Reservation> getReservations() {
        return reservationDAO.getAll();
    }

    @Override
    public List<ReservationDto> getUserReservations(Long id) {
        return reservationDAO.getAllUser(id);
    }

    @Override
    public Reservation getReservation(Long id) {
        return reservationDAO.getById(id);
    }

    @Override
    public void createReservation(Reservation reservation) throws ModelAttributeException {
        validationReservation(reservation);
        reservationDAO.create(reservation);
    }

    @Override
    public void createReservationUser(Reservation reservation) throws ModelAttributeException, ModelAlreadyExistsException {
        if (!stationDAO.getById(reservation.getStationId()).getStatus().equals("WORK")) {
            throw new ModelAttributeException("Станция не работает");
        }
        String startTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(reservation.getTime());
        String endTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(reservation.getEndTime());
        if (reservationDAO.isReservationExist(reservation.getStationId(), startTimestamp, endTimestamp)) {
            throw new ModelAlreadyExistsException("Станция на данное время занята");
        }
        Random random = new Random();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        reservation.setCode(formatted);
        reservationDAO.create(reservation);
    }

    @Override
    public void deleteReservation(Long id) throws ModelNotFoundException {
        if (reservationDAO.getById(id) == null) {
            throw new ModelNotFoundException("Reservation with id " + id + " not found");
        }
        reservationDAO.delete(id);
    }

    @Override
    public void updateReservation(Long id, Reservation reservation) throws ModelNotFoundException, ModelAttributeException {
        if (reservationDAO.getById(id) == null) {
            throw new ModelNotFoundException("Reservation with id " + id + " not found");
        }
        validationReservation(reservation);
        reservationDAO.update(id, reservation);
    }

    private void validationReservation(Reservation reservation) throws ModelAttributeException {
        if (reservation.getTime() == null || reservation.getStationId() < 0 || reservation.getUserId() < 0
                || reservation.getCode() == null) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
