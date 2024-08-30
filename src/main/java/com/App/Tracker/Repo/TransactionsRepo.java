package com.App.Tracker.Repo;

import com.App.Tracker.Entities.CatType;
import com.App.Tracker.Entities.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionsRepo extends JpaRepository<Transactions, Long> {

        Page<Transactions> findAll(Specification<Transactions> spec, Pageable pageable);
        // Dynamic query to calculate the total amount per category with filters
        @Query("SELECT t.category, SUM(t.amount) FROM Transactions t " +
                "WHERE (:catType IS NULL OR t.category.type = :catType) " +
                "AND (:startDate IS NULL OR t.date >= :startDate) " +
                "AND (:endDate IS NULL OR t.date <= :endDate) " +
                "GROUP BY t.category ORDER BY SUM(t.amount) DESC")
        List<Object[]> getTotalAmountByCategoryWithFilters(@Param("catType") CatType catType,
                                                           @Param("startDate") String startDate,
                                                           @Param("endDate") String endDate);

        // Query to get top 3 types of income/expense/investment
        @Query("SELECT t.category.type, SUM(t.amount) FROM Transactions t " +
                "WHERE (:startDate IS NULL OR t.date >= :startDate) " +
                "AND (:endDate IS NULL OR t.date <= :endDate) " +
                "GROUP BY t.category.type ORDER BY SUM(t.amount) DESC")
        List<Object[]> getTop3CategoriesByAmount(@Param("startDate") String startDate,
                                                 @Param("endDate") String endDate);

        // Dynamic query for comparisons (month-to-month or week-to-week)
        @Query("SELECT FUNCTION('DATE_FORMAT', t.date, :dateFormat) as period, SUM(t.amount) FROM Transactions t " +
                "WHERE (:catType IS NULL OR t.category.type = :catType) " +
                "AND (:startDate IS NULL OR t.date >= :startDate) " +
                "AND (:endDate IS NULL OR t.date <= :endDate) " +
                "GROUP BY period ORDER BY period")
        List<Object[]> getComparisonsByPeriod(@Param("catType") CatType catType,
                                              @Param("startDate") String startDate,
                                              @Param("endDate") String endDate,
                                              @Param("dateFormat") String dateFormat);

}
