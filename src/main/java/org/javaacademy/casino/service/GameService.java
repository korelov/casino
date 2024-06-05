package org.javaacademy.casino.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.javaacademy.casino.entity.Game;
import org.javaacademy.casino.repository.FinanceResultRepository;
import org.javaacademy.casino.repository.GameRepository;
import org.javaacademy.casino.service.enums.GameCombination;
import org.javaacademy.casino.service.enums.GameSymbol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GameService {
    private static final int COUNT_SYMBOL_IN_ONE_GAME = 3;
    private static final BigDecimal PAY_FOR_ONE_GAME = BigDecimal.valueOf(15);
    private final Random random = new Random(1);
    private final TransactionTemplate transactionTemplate;
    private final FinanceResultRepository financeResultRepository;
    private final GameRepository gameRepository;

    private String getCombination() {
        return IntStream.range(0, COUNT_SYMBOL_IN_ONE_GAME).
                boxed().
                map(e -> GameSymbol.values()[random.nextInt(GameSymbol.values().length)].name())
                .collect(Collectors.joining());
//        StringBuilder stringBuilder = new StringBuilder();
//        GameSymbol symbol = GameSymbol.values()[random.nextInt(GameSymbol.values().length)];
//        stringBuilder.append(symbol).append(symbol).append(symbol);
//        System.out.println(stringBuilder);
//        return stringBuilder.toString();
    }
//    public String playGame(){
//        return getCombination();
//    }

    public String playGame() {
        return transactionTemplate.execute(status -> {
            Object savepoint = status.createSavepoint();
            financeResultRepository.addOutcome(PAY_FOR_ONE_GAME);
            String combination = getCombination();
            String[] gameSymbols = combination.split("");
            Game game = new Game(gameSymbols[0], gameSymbols[1], gameSymbols[2]);
            gameRepository.create(game);

            if (GameCombination.isFreeCombination(combination)) {
                status.rollbackToSavepoint(savepoint);
                return "Бесплатный ход";
            }
            if (GameCombination.isWinGameCombination(combination)) {
                Integer winRate = GameCombination.getWinRate(combination);
                financeResultRepository.addIncome(BigDecimal.valueOf(winRate));
                return "Вы выграли " + GameCombination.getWinRate(combination);
            }
            return "Вы проиграли";
        });
    }
}
