package org.javaacademy.casino.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.javaacademy.casino.entity.Game;
import org.javaacademy.casino.repository.FinanceResultRepository;
import org.javaacademy.casino.repository.GameRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("test")
@RequiredArgsConstructor
public class InitService {
    private final GameRepository gameRepository;
    private final FinanceResultRepository financeResultRepository;
    private final JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init() {
        gameRepository.create(new Game("A", "A", "A"));
        gameRepository.create(new Game("A", "D", "F"));
        gameRepository.create(new Game("A", "A", "F"));
        gameRepository.create(new Game("D", "D", "F"));

        System.out.println(gameRepository.findAllByPotion(2));
        jdbcTemplate.execute("truncate table game");

        financeResultRepository.addIncome(BigDecimal.TEN);
        financeResultRepository.addOutcome(BigDecimal.ONE);

        System.out.println(financeResultRepository.findResult());
        jdbcTemplate.execute("truncate table finance_result");
    }
}
