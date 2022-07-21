package com.example.chargecracker.service;

import com.example.chargecracker.dto.PagesStatisticDto;
import com.example.chargecracker.dto.StatisticDto;

import java.util.List;

public interface StatisticService {
    List<StatisticDto> getAllStatistic();

    List<StatisticDto> getAllStatisticByCurrentYear();

    List<StatisticDto> getAllStatisticByCurrentMonth();

    PagesStatisticDto getAllStationsHoursStatistic();

    PagesStatisticDto getAllStationsHoursStatisticYear();

    PagesStatisticDto getAllStationsHoursStatisticMonth();

    PagesStatisticDto getAllPage();

    PagesStatisticDto getAllPageByCurrentYear();

    PagesStatisticDto getAllPageByCurrentMonth();
}
