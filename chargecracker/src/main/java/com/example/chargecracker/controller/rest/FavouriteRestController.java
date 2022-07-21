package com.example.chargecracker.controller.rest;

import com.example.chargecracker.model.Favourite;
import com.example.chargecracker.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavouriteRestController {
    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/")
    public ResponseEntity<List<Favourite>> getAllFavourites() {
        return ResponseEntity.ok(favouriteService.getFavourites());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Favourite> getFavouriteById(@PathVariable Long id) {
        return ResponseEntity.ok(favouriteService.getFavourite(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createFavourite(@RequestBody Favourite favorite) {
        try {
            favouriteService.createFavourite(favorite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateFavourite(@PathVariable Long id, @RequestBody Favourite favorite) {
        try {
            favouriteService.updateFavourite(id, favorite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteFavourite(@PathVariable Long id) {
        try {
            favouriteService.deleteFavourite(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
