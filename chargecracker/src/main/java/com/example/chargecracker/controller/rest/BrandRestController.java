package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Brand;
import com.example.chargecracker.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrand(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        try {
            brandService.createBrand(brand);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        try {
            brandService.updateBrand(id, brand);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrand(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
