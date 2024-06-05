package org.javaacademy.casino.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.casino.entity.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(Game game) {
        String sql = """
                insert into game ("1_sym","2_sym","3_sym") values (?,?,?)
                """;
        jdbcTemplate.update(sql, game.getSym1(), game.getSym2(), game.getSym3());
    }

    public List<Game> findAllByPotion(int potionSize) {
        String sql = """
                select * from game order by id desc limit ?
                """;
        return jdbcTemplate.queryForStream(sql, this::gameRowMapper, potionSize).toList();
    }


    private Game gameRowMapper(ResultSet rs, int rowNumber) {
        try {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setSym1(rs.getString("1_sym"));
            game.setSym2(rs.getString("2_sym"));
            game.setSym3(rs.getString("3_sym"));
            return game;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
