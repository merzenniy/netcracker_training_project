package com.example.chargecracker.service;

import com.example.chargecracker.dto.StationConnectorsDto;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Station;

import java.util.List;

public interface StationService {
    List<Station> getStations();

    List<StationConnectorsDto> getStationsAndConnectors();

    List<StationConnectorsDto> getStationsOrderByPriceAsc();

    List<StationConnectorsDto> getStationsOrderByPriceDesc();

    List<StationConnectorsDto> getStationsOrderByRateAsc();

    List<StationConnectorsDto> getStationsOrderByRateDesc();

    List<StationConnectorsDto> getUserFavouriteStations(Long id) throws ModelNotFoundException;

    List<StationConnectorsDto> getUserFavouriteStationsOrderByPriceAsc(Long id) throws ModelNotFoundException;

    List<StationConnectorsDto> getUserFavouriteStationsOrderByPriceDesc(Long id) throws ModelNotFoundException;

    List<StationConnectorsDto> getUserFavouriteStationsOrderByRateAsc(Long id) throws ModelNotFoundException;

    List<StationConnectorsDto> getUserFavouriteStationsOrderByRateDesc(Long id) throws ModelNotFoundException;

    List<StationConnectorsDto> getUserAutoStations(Long id) throws ModelAttributeException;

    Station getStation(Long id);

    StationConnectorsDto getFullStationInformation(Long id) throws ModelNotFoundException, ModelAttributeException;

    void createStation(Station station) throws ModelAttributeException;

    void deleteStation(Long id) throws ModelNotFoundException;

    void updateStation(Long id, Station station) throws ModelNotFoundException, ModelAttributeException;

    void updateStationRate(Long id) throws ModelNotFoundException;
}
