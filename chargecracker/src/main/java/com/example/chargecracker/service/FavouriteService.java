package com.example.chargecracker.service;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Favourite;

import java.util.List;

public interface FavouriteService {
    List<Favourite> getFavourites();

    Favourite getFavourite(Long id);

    Favourite getFavourite(Long userId, Long stationId);

    void createFavourite(Favourite favourite) throws ModelAttributeException;

    void createFavourite(Long userId, Long stationId) throws ModelAttributeException, ModelAlreadyExistsException;

    void deleteFavourite(Long id) throws ModelNotFoundException;

    void deleteFavourite(Long userId, Long stationId) throws ModelNotFoundException;

    void updateFavourite(Long id, Favourite favourite) throws ModelNotFoundException, ModelAttributeException;
}
