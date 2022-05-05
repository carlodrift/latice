package fun.saelatice.latice;

import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void init() {
        this.game = new Game();
    }

    @Test
    void Should_Divide_Tiles_Evenly_When_Distributed() {
        int size = TileColor.getList().size() * TileShape.getList().size();
        this.game.divideTiles();
        Assertions.assertTrue(this.game.getPlayer1().getPool().size() == size
                && this.game.getPlayer2().getPool().size() == size
        );
    }
}
