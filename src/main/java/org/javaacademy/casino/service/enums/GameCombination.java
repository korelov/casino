package org.javaacademy.casino.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GameCombination {
    AAA(10, false),
    FFF(20, false),
    DDD(50, false),
    AFD(null, true);

    private Integer WinRate;
    private boolean isFreeGame;

    public static boolean isFreeCombination(String combination) {
        return Arrays.stream(GameCombination.values())
                .filter(gameCombination -> gameCombination.isFreeGame)
                .anyMatch(gameCombination -> gameCombination.name().equals(combination));
    }

    public static boolean isWinGameCombination(String combination) {
        return Arrays.stream(GameCombination.values())
                .anyMatch(gameCombination -> gameCombination.name().equals(combination)
                        && gameCombination.getWinRate() != null
                        && gameCombination.WinRate > 0);
    }

    public static Integer getWinRate(String combination) {
        return Arrays.stream(GameCombination.values())
                .filter(gameCombination -> gameCombination.name().equals(combination))
                .map(gameCombination -> gameCombination.getWinRate())
                .findFirst()
                .orElse(0);
    }
}
