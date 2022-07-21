package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Rate;
import com.example.chargecracker.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateRestController {
    @Autowired
    private RateService rateService;

    @GetMapping("/")
    public ResponseEntity<List<Rate>> getAllRates() {
        return ResponseEntity.ok(rateService.getRates());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Rate> getRateById(@PathVariable Long id) {
        return ResponseEntity.ok(rateService.getRate(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createRate(@RequestBody Rate rate) {
        try {
            rateService.createRate(rate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateRate(@PathVariable Long id, @RequestBody Rate rate) {
        try {
            rateService.updateRate(id, rate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteRate(@PathVariable Long id) {
        try {
            rateService.deleteRate(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
