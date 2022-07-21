package com.example.chargecracker.controller.web;

import com.example.chargecracker.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {
    @Autowired
    private StatisticService statisticService;


    @GetMapping("/admin/statistics")
    public String getStatistic(Model model) {
        model.addAttribute("statistics", statisticService.getAllStatistic());
        model.addAttribute("label", "Статистика посещения сайта за всё время");
        return "admin/statistic/statistic-list";
    }

    @GetMapping("/admin/statistics-year")
    public String getStatisticByCurrentYear(Model model) {
        model.addAttribute("statistics", statisticService.getAllStatisticByCurrentYear());
        model.addAttribute("label", "Статистика посещения сайта за текущий год");
        return "admin/statistic/statistic-list";
    }

    @GetMapping("/admin/statistics-month")
    public String getStatisticByCurrentMoth(Model model) {
        model.addAttribute("statistics", statisticService.getAllStatisticByCurrentMonth());
        model.addAttribute("label", "Статистика посещения сайта за текущий месяц");
        return "admin/statistic/statistic-list";
    }

    @GetMapping("/admin/statistics-page")
    public String getPageStatistic(Model model) {
        model.addAttribute("statistics", statisticService.getAllPage());
        return "admin/statistic/page-statistic";
    }

    @GetMapping("/admin/statistics-page-year")
    public String getPageStatisticByCurrentYear(Model model) {
        model.addAttribute("statistics", statisticService.getAllPageByCurrentYear());
        return "admin/statistic/page-statistic";
    }

    @GetMapping("/admin/statistics-page-month")
    public String getPageStatisticByCurrentMonth(Model model) {
        model.addAttribute("statistics", statisticService.getAllPageByCurrentMonth());
        return "admin/statistic/page-statistic";
    }

    @GetMapping("/admin/statistics-stations-hours")
    public String getStationsHoursStatistic(Model model) {
        model.addAttribute("statistics", statisticService.getAllStationsHoursStatistic());
        return "admin/statistic/page-statistic";
    }

    @GetMapping("/admin/statistics-stations-hours-year")
    public String getStationsHoursStatisticYear(Model model) {
        model.addAttribute("statistics", statisticService.getAllStationsHoursStatisticYear());
        return "admin/statistic/page-statistic";
    }

    @GetMapping("/admin/statistics-stations-hours-month")
    public String getStationsHoursStatisticMonth(Model model) {
        model.addAttribute("statistics", statisticService.getAllStationsHoursStatisticMonth());
        return "admin/statistic/page-statistic";
    }
}
