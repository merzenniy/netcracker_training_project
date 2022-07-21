package com.example.chargecracker.dao;

import com.example.chargecracker.model.Favourite;

import java.util.List;

public interface FavouriteDAO {
    List<Favourite> getAll();

    Favourite getById(Long id);

    Favourite getByUserIdAndStationId(Long userId, Long stationId);

    void create(Favourite favorite);

    void delete(Long id);

    void delete(Long userId, Long stationId);

    void update(Long id, Favourite favorite);
}
