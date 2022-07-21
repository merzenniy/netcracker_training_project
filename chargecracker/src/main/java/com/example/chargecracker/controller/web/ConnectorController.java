package com.example.chargecracker.controller.web;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Connector;
import com.example.chargecracker.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ConnectorController {
    @Autowired
    private ConnectorService connectorService;

    @GetMapping("/admin/connectors")
    public String getAllConnectors(Model model) {
        model.addAttribute("connectors", connectorService.getConnectors());
        return "admin/connector/connector-list";
    }

    @PostMapping("/admin/connector-delete")
    public String deleteConnector(@RequestParam Long id, Model model) {
        try {
            connectorService.deleteConnector(id);
            return "redirect:/admin/connectors";
        } catch (ModelNotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/admin/connector-create")
    public String createConnectorForm(Model model) {
        model.addAttribute("connector", new Connector());
        return "admin/connector/connector-create";
    }

    @GetMapping("/admin/connector-update/{id}")
    public String updateConnectorForm(@PathVariable Long id, Model model) {
        model.addAttribute("connector", connectorService.getConnector(id));
        return "admin/connector/connector-update";
    }

    @PostMapping("/admin/connector-create")
    public String createConnector(@ModelAttribute Connector connector, Model model) {
        try {
            connectorService.createConnector(connector);
            return "redirect:/admin/connectors";
        } catch (ModelAttributeException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/connector/connector-create";
        }
    }

    @PostMapping("/admin/connector-update")
    public String updateConnector(@ModelAttribute Connector connector, Model model) {
        try {
            connectorService.updateConnector(connector.getId(), connector);
            return "redirect:/admin/connectors";
        } catch (ModelNotFoundException | ModelAttributeException | ModelAlreadyExistsException e) {
            model.addAttribute("exception", e.getMessage());
            return "admin/connector/connector-update";
        }
    }
}
