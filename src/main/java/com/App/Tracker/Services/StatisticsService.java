package com.App.Tracker.Services;

import com.App.Tracker.Entities.CatType;
import com.App.Tracker.Repo.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private TransactionsRepo transactionsRepository;

    public List<Object[]> getTotalAmountByCategoryWithFilters(CatType catType, String startDate, String endDate) {
        return transactionsRepository.getTotalAmountByCategoryWithFilters(catType, startDate, endDate);
    }

    public List<Object[]> getTop3CategoriesByAmount(String startDate, String endDate) {
        return transactionsRepository.getTop3CategoriesByAmount(startDate, endDate).stream().limit(3).toList();
    }

    public List<Object[]> getComparisonsByPeriod(CatType catType, String startDate, String endDate, String dateFormat) {
        return transactionsRepository.getComparisonsByPeriod(catType, startDate, endDate, dateFormat);
    }
}
