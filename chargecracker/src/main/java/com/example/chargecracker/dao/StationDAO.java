package com.example.chargecracker.dao;

import com.example.chargecracker.model.Station;

import java.util.List;

public interface StationDAO {
    List<Station> getAll();

    List<Station> getAllOrderByPriceAsc();

    List<Station> getAllOrderByPriceDesc();

    List<Station> getAllOrderByRateAsc();

    List<Station> getAllOrderByRateDesc();

    List<Station> getAllFavouriteOrderByPriceAsc(Long id);

    List<Station> getAllFavouriteOrderByPriceDesc(Long id);

    List<Station> getAllFavouriteOrderByRateAsc(Long id);

    List<Station> getAllFavouriteOrderByRateDesc(Long id);

    List<Station> getAllUserFavourite(Long id);

    List<Station> getAllByUserAuto(Long id);

    Station getById(Long id);

    void create(Station station);

    void delete(Long id);

    void update(Long id, Station station);

    void updateRate(Long id);
}
