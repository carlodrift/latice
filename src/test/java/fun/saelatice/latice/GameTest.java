package fun.saelatice.latice;

import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private Game game;

    @BeforeEach
    void init() {
        this.game = new Game();
    }

    @Test
    void Should_Divide_Tiles_Evenly_When_Distributed() {
        int size = TileColor.values().length * TileShape.values().length;
        this.game.divideTiles();
        assertTrue(this.game.getPlayer1().getPool().size() == size
                && this.game.getPlayer2().getPool().size() == size
        );
    }

    @Test
    void Should_Pass_To_Next_Player_When_Method_Called() {
        this.game.start();
        Player playerBefore = this.game.getCurrentPlayer();
        this.game.nextPlayer();
        Player playerAfter = this.game.getCurrentPlayer();
        assertNotSame(playerBefore, playerAfter);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 0",
            "2, 1",
            "4, 2",
            "8, 4",
    })
    void Should_Count_Cycles_Properly(int turns, int cycles) {
        this.game.start();
        for (int i = 0; i <= turns; i++) {
            this.game.nextPlayer();
        }
        assertEquals(cycles, this.game.getCycles());
    }
}
