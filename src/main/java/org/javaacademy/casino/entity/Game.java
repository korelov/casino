package org.javaacademy.casino.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
    private Integer id;
    private String sym1;
    private String sym2;
    private String sym3;

    public Game(String sym1, String sym2, String sym3) {
        this.sym1 = sym1;
        this.sym2 = sym2;
        this.sym3 = sym3;
    }

    public String getCombination() {
        return sym1 + sym2 + sym3;
    }
}
