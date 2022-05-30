package fun.saelatice.latice;

import fun.saelatice.latice.model.Board;
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
import static org.junit.jupiter.api.Assertions.fail;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
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
        this.game.goNextPlayer();
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
            this.game.goNextPlayer();
        }
        assertEquals(cycles, this.game.getCycles());
    }

    @Test
    void Should_Declare_Player_1_Winner_When_They_Have_Less_Tiles() {
        this.game.start();
        this.game.getPlayer1().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, 2)));
        assertEquals(this.game.winner(), this.game.getPlayer1());
    }

    @Test
    void Should_Declare_Player_2_Winner_When_They_Have_Less_Tiles() {
        this.game.start();
        this.game.getPlayer2().setRack(new ArrayList<>(this.game.getPlayer1().getRack().subList(0, 2)));
        assertEquals(this.game.winner(), this.game.getPlayer2());
    }

    @Test
    void Should_Be_A_Tie_When_Same_Number_Of_Tiles() {
        this.game.start();
        assertNull(this.game.winner());
    }

    @Test
    void Should_Remove_Tile_From_Rack_When_Played() {
        this.game.start();
        Tile redBowser = new Tile(TileColor.RED, TileShape.BOWSER);
        List<Tile> rack = new ArrayList<>(this.game.getPlayer1().getRack());
        for (int i = 0; i < 2; i++) {
            this.game.getPlayer1().getPool().remove(redBowser);
        }
        rack.set(1, redBowser);
        this.game.getBoard().playTile(new Position(5, 3), rack.get(1), this.game.getPlayer1());
        assertNotEquals(rack, this.game.getPlayer1().getRack());
    }

    @Test
    void Should_Give_Points_When_A_Tile_Is_Played() {
        this.game.start();
        Tile orangeBowser = new Tile(TileColor.ORANGE, TileShape.BOWSER);
        this.game.getBoard().playTile(new Position(2, 2), orangeBowser, this.game.getPlayer1());
        assertEquals(2, this.game.getPlayer1().getPoints());
    }
    
    @Test
    void Should_Remove_Points_When_A_Tile_Is_Played_With_No_More_Free_Move() {
        this.game.start();
        Tile greenYoshi = new Tile(TileColor.GREEN, TileShape.YOSHI);
        Tile pinkPeach = new Tile(TileColor.PINK, TileShape.PEACH);
        this.game.getBoard().playTile(new Position(6, 2), greenYoshi, this.game.getPlayer2());
        int pointsBefore = this.game.getPlayer2().getPoints();
        this.game.getBoard().playTile(new Position(4, 1), pinkPeach, this.game.getPlayer2());
        int pointsAfter = this.game.getPlayer2().getPoints();
        assertEquals(pointsBefore - pointsAfter, Board.MOVE_PRICE);
    }

    @Test
    void Should_End_Game_When_Player_Has_No_More_Tiles() {
        this.game.start();
        Player joueur = this.game.getCurrentPlayer();
        joueur.getPool().clear();
        joueur.getRack().clear();
        this.game.checkOver();
        assertTrue(this.game.isOver());
    }

    @Test
    void Should_End_Game_When_10_Cycles() {
        this.game.start();
        for (int i = 0; i < Game.MAX_CYCLES * 2; i++) {
            this.game.goNextPlayer();
        }
        this.game.checkOver();
        assertTrue(this.game.isOver());
    }
}
