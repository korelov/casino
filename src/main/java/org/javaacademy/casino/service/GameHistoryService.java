package org.javaacademy.casino.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.casino.dto.GameHistoryDto;
import org.javaacademy.casino.dto.GameResultDto;
import org.javaacademy.casino.entity.FinanceResult;
import org.javaacademy.casino.entity.Game;
import org.javaacademy.casino.repository.FinanceResultRepository;
import org.javaacademy.casino.repository.GameRepository;
import org.javaacademy.casino.service.enums.GameCombination;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameHistoryService {

    private final FinanceResultRepository financeResultRepository;
    private final GameRepository gameRepository;

    public GameHistoryDto getHistiry() {
        GameHistoryDto dto = new GameHistoryDto();
        FinanceResult financeResult = financeResultRepository.findResult().orElseThrow();
        dto.setPlayerIncome(financeResult.getIncome());
        dto.setPlayerOutcome(financeResult.getOutcome());

        List<Game> lastFiveGame = gameRepository.findAllByPotion(5);
        List<GameResultDto> gameResultDtos = lastFiveGame.stream()
                .map(game -> {
                    GameResultDto gameResultDto = new GameResultDto();
                    String combination = game.getCombination();
                    gameResultDto.setCombination(combination);
                    gameResultDto.setWinIncome(GameCombination.getWinRate(combination));
                    return gameResultDto;
                }).toList();
        dto.setGameHistory(gameResultDtos);
        return dto;
    }

}
