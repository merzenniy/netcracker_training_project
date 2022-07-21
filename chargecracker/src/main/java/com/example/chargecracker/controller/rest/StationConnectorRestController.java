package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.StationConnector;
import com.example.chargecracker.service.StationConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations-connectors")
public class StationConnectorRestController {
    @Autowired
    private StationConnectorService stationConnectorService;

    @GetMapping("/")
    public ResponseEntity<List<StationConnector>> getAllStationsConnectors() {
        return ResponseEntity.ok(stationConnectorService.getStationsConnectors());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<StationConnector> getStationConnectorById(@PathVariable Long id) {
        return ResponseEntity.ok(stationConnectorService.getStationConnector(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createStationConnector(@RequestBody StationConnector stationConnector) {
        try {
            stationConnectorService.createStationConnector(stationConnector);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateStationConnector(@PathVariable Long id, @RequestBody StationConnector stationConnector) {
        try {
            stationConnectorService.updateStationConnector(id, stationConnector);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteStationConnector(@PathVariable Long id) {
        try {
            stationConnectorService.deleteStationConnector(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
