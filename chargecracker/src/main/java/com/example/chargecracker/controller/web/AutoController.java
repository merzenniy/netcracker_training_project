package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Auto;
import com.example.chargecracker.model.User;
import com.example.chargecracker.service.AutoService;
import com.example.chargecracker.service.BrandService;
import com.example.chargecracker.service.ConnectorService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AutoController {
    @Autowired
    private AutoService autoService;
    @Autowired
    private UserService userService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ConnectorService connectorService;

    @GetMapping("/admin/autos")
    public String getAllAutos(Model model) {
        model.addAttribute("autos", autoService.getAutos());
        return "admin/auto/auto-list";
    }

    @PostMapping("/admin/auto-delete")
    public String deleteAuto(@RequestParam Long id, Model model) {
        try {
            autoService.deleteAuto(id);
            return "redirect:/admin/autos";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/auto-create")
    public String createAutoForm(Model model) {
        model.addAttribute("auto", new Auto());
        model.addAttribute("brands", brandService.getBrands());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "admin/auto/auto-create";
    }

    @GetMapping("/admin/auto-update/{id}")
    public String updateAutoForm(@PathVariable Long id, Model model) {
        model.addAttribute("auto", autoService.getAuto(id));
        model.addAttribute("brands", brandService.getBrands());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "admin/auto/auto-update";
    }

    @PostMapping("/admin/auto-create")
    public String createAuto(@ModelAttribute Auto auto, Model model) {
        try {
            autoService.createAuto(auto);
            return "redirect:/admin/autos";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/auto/auto-create";
        }
    }

    @PostMapping("/admin/auto-update")
    public String updateAuto(@ModelAttribute Auto auto, Model model) {
        try {
            autoService.updateAuto(auto.getId(), auto);
            return "redirect:/admin/autos";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/auto/auto-update";
        }
    }

    @GetMapping("/user/create-auto")
    public String createUserAutoForm(Model model) {
        model.addAttribute("auto", new Auto());
        model.addAttribute("brands", brandService.getBrands());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "auto/auto-create";
    }

    @PostMapping("/user/create-auto")
    public String createUserAuto(@ModelAttribute Auto auto, @AuthenticationPrincipal User currentUser, Model model) {
        try {
            autoService.createUserAuto(currentUser.getId(), auto);
            return "redirect:/user/profile";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "auto/auto-create";
        }
    }

    @GetMapping("/user/update-auto")
    public String updateUserAutoForm(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("auto", autoService.getAuto(userService.getUser(currentUser.getId()).getAutoId()));
        model.addAttribute("brands", brandService.getBrands());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "auto/auto-update";
    }

    @PostMapping("/user/update-auto")
    public String updateUserAuto(@ModelAttribute Auto auto, @AuthenticationPrincipal User currentUser, Model model) {
        try {
            autoService.updateAuto(userService.getUser(currentUser.getId()).getAutoId(), auto);
            return "redirect:/user/profile";
        } catch (ModelNotFoundException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "auto/auto-update";
        }
    }
}
