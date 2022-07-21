package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Brand;
import com.example.chargecracker.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/admin/brands")
    public String getAllBrands(Model model) {
        model.addAttribute("brands", brandService.getBrands());
        return "admin/brand/brand-list";
    }

    @GetMapping("/admin/brand-update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.getBrand(id));
        return "admin/brand/brand-update";
    }

    @PostMapping("/admin/brand-update")
    public String updateUser(@ModelAttribute Brand brand, Model model) {
        try {
            brandService.updateBrand(brand.getId(), brand);
            return "redirect:/admin/brands";
        } catch (ModelNotFoundException | ModelAttributeException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/brand/brand-update";
        }
    }

    @GetMapping("/admin/brand-create")
    public String createUserForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand/brand-create";
    }

    @PostMapping("/admin/brand-create")
    public String createBrand(@ModelAttribute Brand brand, Model model) {
        try {
            brandService.createBrand(brand);
            return "redirect:/admin/brands";
        } catch (ModelAlreadyExistsException | ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/brand/brand-create";
        }
    }

    @PostMapping("/admin/brand-delete")
    public String deleteBrand(@RequestParam Long id, Model model) {
        try {
            brandService.deleteBrand(id);
            return "redirect:/admin/brands";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }
}
