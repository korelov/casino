package org.javaacademy.casino.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.casino.entity.FinanceResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FinanceResultRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addOutcome(BigDecimal outcome) {
        String sql = """
                update finance_result set outcome = outcome+ ?
                """;
        jdbcTemplate.update(sql, outcome);
    }

    public void addIncome(BigDecimal income) {
        String sql = """
                update finance_result set income = income+ ?
                """;
        jdbcTemplate.update(sql, income);
    }


    public Optional<FinanceResult> findResult() {
        String sql = """
                select * from finance_result limit 1
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::financeResultMapper));
    }


    private FinanceResult financeResultMapper(ResultSet resultSet, int rowNumber) {
        try {
            FinanceResult financeResult = new FinanceResult();
            financeResult.setIncome(resultSet.getBigDecimal("income"));
            financeResult.setOutcome(resultSet.getBigDecimal("outcome"));
            return financeResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
