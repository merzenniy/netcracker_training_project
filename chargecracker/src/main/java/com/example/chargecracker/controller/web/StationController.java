package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Rate;
import com.example.chargecracker.model.Station;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.RateService;
import com.example.chargecracker.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StationController {
    @Autowired
    private StationService stationService;
    @Autowired
    private RateService rateService;

    @GetMapping("/")
    public String getAllStations(Model model) {
        model.addAttribute("stations", stationService.getStationsAndConnectors());
        return "index";
    }

    @GetMapping("/stations-sort-rate/asc")
    public String getAllStationsSortedByRateAsc(Model model) {
        model.addAttribute("stations", stationService.getStationsOrderByRateAsc());
        return "index";
    }

    @GetMapping("/stations-sort-rate/desc")
    public String getAllStationsSortedByRateDesc(Model model) {
        model.addAttribute("stations", stationService.getStationsOrderByRateDesc());
        return "index";
    }

    @GetMapping("/stations-sort-price/asc")
    public String getAllStationsSortedByPriceAsc(Model model) {
        model.addAttribute("stations", stationService.getStationsOrderByPriceAsc());
        return "index";
    }

    @GetMapping("/stations-sort-price/desc")
    public String getAllStationsSortedByPriceDesc(Model model) {
        model.addAttribute("stations", stationService.getStationsOrderByPriceDesc());
        return "index";
    }

    @GetMapping("/user/favourite/stations-sort-rate/asc")
    public String getAllUserFavouriteStationsSortedByRateAsc(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations",
                    stationService.getUserFavouriteStationsOrderByRateAsc(currentUser.getId()));
            return "station/favourite-station-list";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "station/favourite-station-list";
        }
    }

    @GetMapping("/user/favourite/stations-sort-rate/desc")
    public String getAllUserFavouriteStationsSortedByRateDesc(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations",
                    stationService.getUserFavouriteStationsOrderByRateDesc(currentUser.getId()));
            return "station/favourite-station-list";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "station/favourite-station-list";
        }
    }

    @GetMapping("/user/favourite/stations-sort-price/asc")
    public String getAllUserFavouriteStationsSortedByPriceAsc(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations",
                    stationService.getUserFavouriteStationsOrderByPriceAsc(currentUser.getId()));
            return "station/favourite-station-list";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "station/favourite-station-list";
        }
    }

    @GetMapping("/user/favourite/stations-sort-price/desc")
    public String getAllUserFavouriteStationsSortedByPriceDesc(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations",
                    stationService.getUserFavouriteStationsOrderByPriceDesc(currentUser.getId()));
            return "station/favourite-station-list";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "station/favourite-station-list";
        }
    }

    @GetMapping("/station/{id}")
    public String getStationInformation(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("station", stationService.getFullStationInformation(id));
            model.addAttribute("rates", rateService.getRatesStation(id));
            model.addAttribute("rate", new Rate());
            return "station/station-info";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }

    }

    @GetMapping("/user/favourite-stations")
    public String getFavouriteStations(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations", stationService.getUserFavouriteStations(currentUser.getId()));
            return "station/favourite-station-list";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "station/favourite-station-list";
        }
    }

    @GetMapping("/user/stations")
    public String getUserAutoStations(Model model, @AuthenticationPrincipal User currentUser) {
        try {
            model.addAttribute("stations", stationService.getUserAutoStations(currentUser.getAutoId()));
            return "index";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/admin/stations")
    public String getStationsForAdmin(Model model) {
        model.addAttribute("stations", stationService.getStations());
        return "admin/station/station-list";
    }

    @PostMapping("/admin/station-delete")
    public String deleteStation(@RequestParam Long id, Model model) {
        try {
            stationService.deleteStation(id);
            return "redirect:/admin/stations";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/station-create")
    public String createStationForm(Model model) {
        model.addAttribute("station", new Station());
        return "admin/station/station-create";
    }

    @GetMapping("/admin/station-update/{id}")
    public String updateStationForm(@PathVariable Long id, Model model) {
        model.addAttribute("station", stationService.getStation(id));
        return "admin/station/station-update";
    }

    @PostMapping("/admin/station-create")
    public String createStation(@ModelAttribute Station station, Model model) {
        try {
            stationService.createStation(station);
            return "redirect:/admin/stations";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/station/station-create";
        }
    }

    @PostMapping("/admin/station-update")
    public String updateStation(@ModelAttribute Station station, Model model) {
        try {
            stationService.updateStation(station.getId(), station);
            return "redirect:/admin/stations";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/station/station-update";
        }
    }
}
