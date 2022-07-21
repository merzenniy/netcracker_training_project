package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Rate;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.RateService;
import com.example.chargecracker.service.StationService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private UserService userService;
    @Autowired
    private StationService stationService;

    @GetMapping("/admin/rates")
    public String getAllRates(Model model) {
        model.addAttribute("rates", rateService.getRates());
        return "admin/rate/rate-list";
    }

    @PostMapping("/rate-create")
    public String createRate(Model model, @ModelAttribute Rate rate, @AuthenticationPrincipal User currentUser) {
        try {
            rate.setUserId(currentUser.getId());
            rateService.createRate(rate);
            return "redirect:/station/" + rate.getStationId();
        } catch (ModelAttributeException | ModelNotFoundException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @PostMapping("/rate-update")
    public String updateUserRate(@ModelAttribute Rate rate, Model model) {
        try {
            rateService.updateRate(rate.getId(), rate);
            return "redirect:/station/" + rate.getStationId();
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/rate/rate-update";
        }
    }

    @PostMapping("/admin/rate-delete")
    public String deleteRate(@RequestParam Long id, Model model) {
        try {
            rateService.deleteRate(id);
            return "redirect:/admin/rates";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/rate-create")
    public String createRateForm(Model model) {
        model.addAttribute("rate", new Rate());
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/rate/rate-create";
    }

    @GetMapping("/admin/rate-update/{id}")
    public String updateRateForm(@PathVariable Long id, Model model) {
        model.addAttribute("rate", rateService.getRate(id));
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/rate/rate-update";
    }

    @PostMapping("/admin/rate-create")
    public String createRate(@ModelAttribute Rate rate, Model model) {
        try {
            rateService.createRate(rate);
            return "redirect:/admin/rates";
        } catch (ModelAttributeException | ModelNotFoundException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/rate/rate-create";
        }
    }

    @PostMapping("/admin/rate-update")
    public String updateRate(@ModelAttribute Rate rate, Model model) {
        try {
            rateService.updateRate(rate.getId(), rate);
            return "redirect:/admin/rates";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/rate/rate-update";
        }
    }
}
