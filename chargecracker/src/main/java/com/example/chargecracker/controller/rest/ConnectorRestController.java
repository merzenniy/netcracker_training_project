package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Connector;
import com.example.chargecracker.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connectors")
public class ConnectorRestController {
    @Autowired
    private ConnectorService connectorService;

    @GetMapping("/")
    public ResponseEntity<List<Connector>> getAllConnectors() {
        return ResponseEntity.ok(connectorService.getConnectors());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Connector> getConnectorById(@PathVariable Long id) {
        return ResponseEntity.ok(connectorService.getConnector(id));
    }


    @PostMapping("/")
    public ResponseEntity<?> createConnector(@RequestBody Connector connector) {
        try {
            connectorService.createConnector(connector);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateConnector(@PathVariable Long id, @RequestBody Connector connector) {
        try {
            connectorService.updateConnector(id, connector);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteConnector(@PathVariable Long id) {
        try {
            connectorService.deleteConnector(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
