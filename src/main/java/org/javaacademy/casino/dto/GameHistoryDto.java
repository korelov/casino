package org.javaacademy.casino.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GameHistoryDto {
    private BigDecimal playerIncome;
    private BigDecimal playerOutcome;
    @JsonProperty("game_history")
    private List<GameResultDto> gameHistory;
}
