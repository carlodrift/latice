package fun.saelatice.latice;

import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.Player;
import fun.saelatice.latice.model.Position;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
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
            "8, 4"
    })
    void Should_Count_Cycles_Properly(int turns, int cycles) {
        this.game.start();
        for (int i = 0; i <= turns; i++) {
            this.game.nextPlayer();
        }
        assertEquals(cycles, this.game.getCycles());
    }

    @Test
    void Should_Declare_Player_1_Winner_When_They_Have_Less_Tiles() {
        this.game.start();
        this.game.getPlayer1().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, 2)));
        assertEquals(this.game.getWinner(), this.game.getPlayer1());
    }

    @Test
    void Should_Declare_Player_2_Winner_When_They_Have_Less_Tiles() {
        this.game.start();
        this.game.getPlayer2().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, 2)));
        assertEquals(this.game.getWinner(), this.game.getPlayer2());
    }

    @Test
    void Should_Be_A_Tie_When_Same_Number_Of_Tiles() {
        this.game.start();
        assertNull(this.game.getWinner());
    }

    @Test
    void When_a_tile_is_played_Should_change_rack_and_give_points() {
        this.game.start();
        Tile orangeBowser = new Tile(TileColor.ORANGE, TileShape.BOWSER);
        List<Tile> rack = new ArrayList<>(this.game.getPlayer1().getRack());
        for (int i = 0; i < 2; i++) {
            this.game.getPlayer1().getPool().remove(orangeBowser);
        }
        rack.set(1, orangeBowser);
        List<Tile> oldRack = rack;
        this.game.getBoard().playTile(new Position(2, 2), rack.get(1), this.game.getPlayer1());
        assertEquals(2, this.game.getPlayer1().getPoints());
        assertNotEquals(oldRack, this.game.getPlayer1().getRack());
    }

    @Test
    void Should_be_the_end_of_the_game_when_a_player_have_not_pool_and_rack_anymore() {
        this.game.start();
        Player joueur = this.game.getCurrentPlayer();
        joueur.getPool().clear();
        joueur.getRack().clear();
        this.game.checkOver();
        assertTrue(this.game.isOver());
    }

    @Test
    void Should_be_the_end_of_the_game_after_10_cycles() {
        this.game.start();
        for (int i = 0; i < 20; i++) {
            this.game.nextPlayer();
        }
        this.game.checkOver();
        assertTrue(this.game.isOver());
    }
}
