package com.example.chargecracker.dao;

import com.example.chargecracker.dto.NamedStatisticDto;
import com.example.chargecracker.dto.StatisticDto;
import com.example.chargecracker.model.Statistic;

import java.util.List;

public interface StatisticDAO {
    List<StatisticDto> getAll();

    List<StatisticDto> getAllByCurrentYear();

    List<StatisticDto> getAllByCurrentMonth();

    List<NamedStatisticDto> getAllPage();

    List<NamedStatisticDto> getAllPageByCurrentYear();

    List<NamedStatisticDto> getAllPageByCurrentMonth();

    List<NamedStatisticDto> getAllStationsHours();

    List<NamedStatisticDto> getAllStationHoursYear();

    List<NamedStatisticDto> getAllStationHoursMonth();

    void create(Statistic statistic);
}
