package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Reservation;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.ReservationService;
import com.example.chargecracker.service.StationService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private StationService stationService;

    @GetMapping("/admin/reservations")
    public String getAllReservations(Model model) {
        model.addAttribute("reservations", reservationService.getReservations());
        return "admin/reservation/reservation-list";
    }

    @PostMapping("/admin/reservation-delete")
    public String deleteReservation(@RequestParam Long id, Model model) {
        try {
            reservationService.deleteReservation(id);
            return "redirect:/admin/reservations";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/reservation/station/{id}")
    public String createReservationForm(@PathVariable Long id, Model model) {
        Reservation reservation = new Reservation();
        reservation.setStationId(id);
        model.addAttribute("reservation", reservation);
        return "reservation/reservation-create";
    }

    @PostMapping("/reservation-create")
    public String createReservation(@ModelAttribute Reservation reservation, @AuthenticationPrincipal User currentUser, Model model) {
        try {
            reservation.setUserId(currentUser.getId());
            reservationService.createReservationUser(reservation);
        } catch (ModelAttributeException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
        return "redirect:/";
    }

    @GetMapping("/admin/reservation-create")
    public String createReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/reservation/reservation-create";
    }

    @GetMapping("/admin/reservation-update/{id}")
    public String updateReservationForm(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/reservation/reservation-update";
    }

    @PostMapping("/admin/reservation-create")
    public String createReservation(@ModelAttribute Reservation reservation, Model model) {
        try {
            reservationService.createReservation(reservation);
            return "redirect:/admin/reservations";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/reservation/reservation-create";
        }
    }

    @PostMapping("/admin/reservation-update")
    public String updateReservation(@ModelAttribute Reservation reservation, Model model) {
        try {
            reservationService.updateReservation(reservation.getId(), reservation);
            return "redirect:/admin/reservations";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/reservation/reservation-update";
        }
    }

    @GetMapping("/user/reservations")
    public String getUserReservations(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("reservations", reservationService.getUserReservations(currentUser.getId()));
        return "reservation/reservation-list";
    }
}
