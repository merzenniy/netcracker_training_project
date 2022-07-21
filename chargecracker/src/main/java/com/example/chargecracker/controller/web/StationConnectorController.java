package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.StationConnector;
import com.example.chargecracker.service.ConnectorService;
import com.example.chargecracker.service.StationConnectorService;
import com.example.chargecracker.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StationConnectorController {
    @Autowired
    private StationConnectorService stationConnectorService;
    @Autowired
    private StationService stationService;
    @Autowired
    private ConnectorService connectorService;

    @GetMapping("/admin/stations-connectors")
    public String getAllStationsConnectors(Model model) {
        model.addAttribute("stationsConnectors", stationConnectorService.getStationsConnectors());
        return "admin/stationConnector/station-connector-list";
    }

    @PostMapping("/admin/station-connector-delete")
    public String deleteStationConnector(@RequestParam Long id, Model model) {
        try {
            stationConnectorService.deleteStationConnector(id);
            return "redirect:/admin/stations-connectors";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/station-connector-update/{id}")
    public String updateStationConnectorForm(@PathVariable Long id, Model model) {
        model.addAttribute("stationConnector", stationConnectorService.getStationConnector(id));
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "admin/stationConnector/station-connector-update";
    }

    @PostMapping("/admin/station-connector-update")
    public String updateStationConnector(@ModelAttribute StationConnector stationConnector, Model model) {
        try {
            stationConnectorService.updateStationConnector(stationConnector.getId(), stationConnector);
            return "redirect:/admin/stations-connectors";
        } catch (ModelAttributeException | ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/stationConnector/station-connector-update";
        }
    }

    @GetMapping("/admin/station-connector-create")
    public String createStationConnectorForm(Model model) {
        model.addAttribute("stationConnector", new StationConnector());
        model.addAttribute("stations", stationService.getStations());
        model.addAttribute("connectors", connectorService.getConnectors());
        return "admin/stationConnector/station-connector-create";
    }

    @PostMapping("/admin/station-connector-create")
    public String createStationConnector(@ModelAttribute StationConnector stationConnector, Model model) {
        try {
            stationConnectorService.createStationConnector(stationConnector);
            return "redirect:/admin/stations-connectors";
        } catch (ModelAttributeException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/stationConnector/station-connector-create";
        }
    }
}
