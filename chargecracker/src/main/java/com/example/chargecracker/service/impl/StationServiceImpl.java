package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.StationDAO;
import com.example.chargecracker.dto.StationConnectorsDto;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Station;
import com.example.chargecracker.service.ConnectorService;
import com.example.chargecracker.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private ConnectorService connectorService;

    @Override
    public List<Station> getStations() {
        return stationDAO.getAll();
    }

    @Override
    public List<StationConnectorsDto> getStationsAndConnectors() {
        List<Station> stations = stationDAO.getAll();
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getStationsOrderByPriceAsc() {
        List<Station> stations = stationDAO.getAllOrderByPriceAsc();
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getStationsOrderByPriceDesc() {
        List<Station> stations = stationDAO.getAllOrderByPriceDesc();
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getStationsOrderByRateAsc() {
        List<Station> stations = stationDAO.getAllOrderByRateAsc();
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getStationsOrderByRateDesc() {
        List<Station> stations = stationDAO.getAllOrderByRateDesc();
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getUserFavouriteStations(Long id) throws ModelNotFoundException {
        List<Station> stations = stationDAO.getAllUserFavourite(id);
        if (stations.isEmpty()) {
            throw new ModelNotFoundException("У вас нету избранных станций");
        }
        return getStationsConnectorsDto(stations);
    }

    private List<StationConnectorsDto> getStationsConnectorsDto(List<Station> stations) {
        List<StationConnectorsDto> result = new ArrayList<>();
        stations.forEach(station -> result.add(StationServiceImpl.this.getStationConnectors(station)));
        return result;
    }

    private StationConnectorsDto getStationConnectors(Station station) {
        StationConnectorsDto stationConnectorsDto = new StationConnectorsDto();
        stationConnectorsDto.setConnectors(connectorService.getConnectorsByStationId(station.getId()));
        stationConnectorsDto.setStationId(station.getId());
        stationConnectorsDto.setStatus(station.getStatus());
        stationConnectorsDto.setCordinates(station.getCordinates());
        stationConnectorsDto.setRate(station.getRate());
        stationConnectorsDto.setPrice(station.getPrice());
        return stationConnectorsDto;
    }

    @Override
    public List<StationConnectorsDto> getUserFavouriteStationsOrderByPriceAsc(Long id) throws ModelNotFoundException {
        List<Station> stations = stationDAO.getAllFavouriteOrderByPriceAsc(id);
        if (stations.isEmpty()) {
            throw new ModelNotFoundException("У вас нету избранных станций");
        }
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getUserFavouriteStationsOrderByPriceDesc(Long id) throws ModelNotFoundException {
        List<Station> stations = stationDAO.getAllFavouriteOrderByPriceDesc(id);
        if (stations.isEmpty()) {
            throw new ModelNotFoundException("У вас нету избранных станций");
        }
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getUserFavouriteStationsOrderByRateAsc(Long id) throws ModelNotFoundException {
        List<Station> stations = stationDAO.getAllFavouriteOrderByRateAsc(id);
        if (stations.isEmpty()) {
            throw new ModelNotFoundException("У вас нету избранных станций");
        }
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getUserFavouriteStationsOrderByRateDesc(Long id) throws ModelNotFoundException {
        List<Station> stations = stationDAO.getAllFavouriteOrderByRateDesc(id);
        if (stations.isEmpty()) {
            throw new ModelNotFoundException("У вас нету избранных станций");
        }
        return getStationsConnectorsDto(stations);
    }

    @Override
    public List<StationConnectorsDto> getUserAutoStations(Long id) throws ModelAttributeException {
        if (id == 0) {
            throw new ModelAttributeException("У вас нету авто");
        }
        List<Station> stations = stationDAO.getAllByUserAuto(id);
        return getStationsConnectorsDto(stations);
    }

    @Override
    public Station getStation(Long id) {
        return stationDAO.getById(id);
    }

    @Override
    public StationConnectorsDto getFullStationInformation(Long id) throws ModelNotFoundException, ModelAttributeException {
        Station station = getStation(id);
        if (station == null) {
            throw new ModelNotFoundException("Станция не найдена");
        }
        validationStation(station);
        return getStationConnectors(station);
    }

    @Override
    public void createStation(Station station) throws ModelAttributeException {
        validationStation(station);
        stationDAO.create(station);
    }

    @Override
    public void deleteStation(Long id) throws ModelNotFoundException {
        if (stationDAO.getById(id) == null) {
            throw new ModelNotFoundException("Station with id " + id + " not found");
        }
        stationDAO.delete(id);
    }

    @Override
    public void updateStation(Long id, Station station) throws ModelNotFoundException, ModelAttributeException {
        if (stationDAO.getById(id) == null) {
            throw new ModelNotFoundException("Station with id " + id + " not found");
        }
        validationStation(station);
        stationDAO.update(id, station);
    }

    @Override
    public void updateStationRate(Long id) throws ModelNotFoundException {
        if (stationDAO.getById(id) == null) {
            throw new ModelNotFoundException("Station with id " + id + " not found");
        }
        stationDAO.updateRate(id);
    }

    private void validationStation(Station station) throws ModelAttributeException {
        if (station.getCordinates() == null || station.getCordinates().isEmpty()
                || station.getRate() < 0 || station.getPrice() <= 0) {
            throw new ModelAttributeException("Неправильно введенные данные");
        }
    }
}
