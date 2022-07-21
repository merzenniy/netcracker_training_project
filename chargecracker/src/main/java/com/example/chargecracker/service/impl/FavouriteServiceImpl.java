package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.FavouriteDAO;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Favourite;
import com.example.chargecracker.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    private FavouriteDAO favouriteDAO;

    @Override
    public List<Favourite> getFavourites() {
        return favouriteDAO.getAll();
    }

    @Override
    public Favourite getFavourite(Long id) {
        return favouriteDAO.getById(id);
    }

    @Override
    public Favourite getFavourite(Long userId, Long stationId) {
        return favouriteDAO.getByUserIdAndStationId(userId, stationId);
    }

    @Override
    public void createFavourite(Favourite favourite) throws ModelAttributeException {
        validationFavourite(favourite);
        favouriteDAO.create(favourite);
    }

    @Override
    public void createFavourite(Long userId, Long stationId) throws ModelAttributeException, ModelAlreadyExistsException {
        if (getFavourite(userId, stationId) != null) {
            throw new ModelAlreadyExistsException("Станция уже находиться в избранных станциях");
        }
        Favourite favourite = new Favourite();
        favourite.setStationId(stationId);
        favourite.setUserId(userId);
        validationFavourite(favourite);
        favouriteDAO.create(favourite);
    }

    @Override
    public void deleteFavourite(Long id) throws ModelNotFoundException {
        if (favouriteDAO.getById(id) == null) {
            throw new ModelNotFoundException("Favorite with id " + id + " not found");
        }
        favouriteDAO.delete(id);
    }

    @Override
    public void deleteFavourite(Long userId, Long stationId) throws ModelNotFoundException {
        if (favouriteDAO.getByUserIdAndStationId(userId, stationId) == null) {
            throw new ModelNotFoundException(
                    "The user with id " + userId + " doesn't have a station with id" + stationId + " in the favorites");
        }
        favouriteDAO.delete(userId, stationId);
    }

    @Override
    public void updateFavourite(Long id, Favourite favourite) throws ModelNotFoundException, ModelAttributeException {
        if (favouriteDAO.getById(id) == null) {
            throw new ModelNotFoundException("Favorite with id " + id + " not found");
        }
        validationFavourite(favourite);
        favouriteDAO.update(id, favourite);
    }

    private void validationFavourite(Favourite favourite) throws ModelAttributeException {
        if (favourite.getStationId() == null || favourite.getUserId() == null) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
