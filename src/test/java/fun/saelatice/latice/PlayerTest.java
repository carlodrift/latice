package fun.saelatice.latice;

import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    private Game game;

    @BeforeEach
    void init() {
        this.game = new Game();
        this.game.divideTiles();
        this.game.getPlayer1().changeRack(false);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 10",
            "4, 5",
            "0, 4",
            "3, 3"
    })
    void Should_Change_Tiles_When_Rack_Changed(int rackSize, int poolSize) {
        this.game.getPlayer1().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, rackSize)));
        this.game.getPlayer1().setPool(new ArrayList<>(this.game.getPlayer1().getPool().subList(0, poolSize)));
        List<Tile> oldTiles = new ArrayList<>(this.game.getPlayer1().getRack());
        this.game.getPlayer1().changeRack(false);
        List<Tile> newTiles = new ArrayList<>(this.game.getPlayer1().getRack());
        for (int i = 0; i < oldTiles.size(); i++) {
            assertNotSame(oldTiles.get(i), newTiles.get(i));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "3, 0",
            "2, 1"
    })
    void Should_Not_Be_Able_To_Change_Rack_When_Smaller_Pool(int rackSize, int poolSize) {
        this.game.getPlayer1().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, rackSize)));
        this.game.getPlayer1().setPool(new ArrayList<>(this.game.getPlayer1().getPool().subList(0, poolSize)));
        assertFalse(this.game.getPlayer1().canChangeRack());
    }

    @ParameterizedTest
    @CsvSource({
            "4, 10",
            "4, 5",
            "0, 4",
            "3, 3"
    })
    void Should_Be_Able_To_Change_Rack_When_Enough_Pool(int rackSize, int poolSize) {
        this.game.getPlayer1().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, rackSize)));
        this.game.getPlayer1().setPool(new ArrayList<>(this.game.getPlayer1().getPool().subList(0, poolSize)));
        assertTrue(this.game.getPlayer1().canChangeRack());
    }
}
