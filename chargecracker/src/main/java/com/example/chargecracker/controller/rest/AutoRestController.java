package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Auto;
import com.example.chargecracker.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutoRestController {
    @Autowired
    private AutoService autoService;

    @GetMapping("/")
    public ResponseEntity<List<Auto>> getAllAutos() {
        return ResponseEntity.ok(autoService.getAutos());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Auto> getAutoById(@PathVariable Long id) {
        return ResponseEntity.ok(autoService.getAuto(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createAuto(@RequestBody Auto auto) {
        try {
            autoService.createAuto(auto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateAuto(@PathVariable Long id, @RequestBody Auto auto) {
        try {
            autoService.updateAuto(id, auto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteAuto(@PathVariable Long id) {
        try {
            autoService.deleteAuto(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
