package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.StatisticDAO;
import com.example.chargecracker.dto.NamedStatisticDto;
import com.example.chargecracker.dto.PagesStatisticDto;
import com.example.chargecracker.dto.StatisticDto;
import com.example.chargecracker.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticDAO statisticDAO;

    @Override
    public List<StatisticDto> getAllStatistic() {
        return statisticDAO.getAll();
    }

    @Override
    public List<StatisticDto> getAllStatisticByCurrentYear() {
        return statisticDAO.getAllByCurrentYear();
    }

    @Override
    public List<StatisticDto> getAllStatisticByCurrentMonth() {
        return statisticDAO.getAllByCurrentMonth();
    }

    @Override
    public PagesStatisticDto getAllStationsHoursStatistic() {
        return pageStatisticCollection(statisticDAO.getAllStationsHours());
    }

    @Override
    public PagesStatisticDto getAllStationsHoursStatisticYear() {
        return pageStatisticCollection(statisticDAO.getAllStationHoursYear());
    }

    @Override
    public PagesStatisticDto getAllStationsHoursStatisticMonth() {
        return pageStatisticCollection(statisticDAO.getAllStationHoursMonth());
    }

    @Override
    public PagesStatisticDto getAllPage() {
        return pageStatisticCollection(statisticDAO.getAllPage());
    }

    @Override
    public PagesStatisticDto getAllPageByCurrentYear() {
        return pageStatisticCollection(statisticDAO.getAllPageByCurrentYear());
    }

    @Override
    public PagesStatisticDto getAllPageByCurrentMonth() {
        return pageStatisticCollection(statisticDAO.getAllPageByCurrentMonth());
    }

    private PagesStatisticDto pageStatisticCollection(List<NamedStatisticDto> statisticList) {
        List<String> monthsList = statisticList.stream()
                .map(NamedStatisticDto::getCreatedAt)
                .distinct()
                .collect(Collectors.toList());

        Map<String, List<Long>> map = new HashMap<>();

        statisticList.forEach(e -> {
            List<Long> list;
            if (map.containsKey(e.getLabel())) {
                list = map.get(e.getLabel());
            } else {
                list = LongStream.of(new long[monthsList.size()])
                        .boxed()
                        .collect(Collectors.toList());
            }
            int monthIndex = monthsList.indexOf(e.getCreatedAt());
            list.set(monthIndex, e.getCount());
            map.put(e.getLabel(), list);
        });

        PagesStatisticDto pagesStatisticDto = new PagesStatisticDto();
        pagesStatisticDto.setMonths(monthsList);
        pagesStatisticDto.setBusinessOperationCount(map);
        return pagesStatisticDto;
    }
}
