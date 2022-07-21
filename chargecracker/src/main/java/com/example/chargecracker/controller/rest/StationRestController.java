package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Station;
import com.example.chargecracker.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationRestController {
    @Autowired
    private StationService stationService;

    @GetMapping("/")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getStations());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Station> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok(stationService.getStation(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createStation(@RequestBody Station station) {
        try {
            stationService.createStation(station);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Station station) {
        try {
            stationService.updateStation(id, station);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        try {
            stationService.deleteStation(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
