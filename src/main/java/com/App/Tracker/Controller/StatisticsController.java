package com.App.Tracker.Controller;

import com.App.Tracker.Entities.CatType;
import com.App.Tracker.Services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/totamount")
    public List<Object[]> getTotalAmountByCategoryWithFilters(
            @RequestParam(required = false) CatType catType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return statisticsService.getTotalAmountByCategoryWithFilters(catType, startDate, endDate);
    }

    @GetMapping("/top3-cat")
    public List<Object[]> getTop3CategoriesByAmount(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return statisticsService.getTop3CategoriesByAmount(startDate, endDate);
    }

    @GetMapping("/comparisons")
    public List<Object[]> getComparisonsByPeriod(
            @RequestParam(required = false) CatType catType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "%Y-%m") String dateFormat) { // Default is month-to-month
        return statisticsService.getComparisonsByPeriod(catType, startDate, endDate, dateFormat);
    }
}
