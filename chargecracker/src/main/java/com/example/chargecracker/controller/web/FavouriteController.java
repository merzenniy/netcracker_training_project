package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Favourite;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.FavouriteService;
import com.example.chargecracker.service.StationService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private StationService stationService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/favourites")
    public String getAllFavourites(Model model) {
        model.addAttribute("favourites", favouriteService.getFavourites());
        return "admin/favourite/favourite-list";
    }

    @PostMapping("/admin/favourite-delete")
    public String deleteFavourite(@RequestParam Long id, Model model) {
        try {
            favouriteService.deleteFavourite(id);
            return "redirect:/admin/favourites";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/favourite-create")
    public String createFavouriteForm(Model model) {
        model.addAttribute("favourite", new Favourite());
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/favourite/favourite-create";
    }

    @GetMapping("/admin/favourite-update/{id}")
    public String updateFavouriteForm(@PathVariable Long id, Model model) {
        model.addAttribute("favourite", favouriteService.getFavourite(id));
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("users", userService.getUsers());
        return "admin/favourite/favourite-update";
    }

    @PostMapping("/admin/favourite-create")
    public String createFavourite(@ModelAttribute Favourite favourite, Model model) {
        try {
            favouriteService.createFavourite(favourite);
            return "redirect:/admin/favourites";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/favourite/favourite-create";
        }
    }

    @PostMapping("/admin/favourite-update")
    public String updateFavourite(@ModelAttribute Favourite favourite, Model model) {
        try {
            favouriteService.updateFavourite(favourite.getId(), favourite);
            return "redirect:/admin/favourites";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/favourite/favourite-update";
        }
    }

    @PostMapping("/favourite-add")
    public String addFavourite(@RequestParam Long stationId, @AuthenticationPrincipal User currentUser, Model model) {
        try {
            favouriteService.createFavourite(currentUser.getId(), stationId);
            return "redirect:/";
        } catch (ModelAttributeException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("stations", stationService.getStations());
            return "index";
        }
    }

    @PostMapping("/favourite-delete")
    public String deleteUserFavourite(@AuthenticationPrincipal User currentUser, @RequestParam Long stationId, Model model) {
        try {
            favouriteService.deleteFavourite(currentUser.getId(), stationId);
            return "redirect:/user/favourite-stations";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }
}
