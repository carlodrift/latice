package fun.saelatice.latice.tile;

import fun.saelatice.latice.model.Game;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    private List<Tile> tiles;

    @BeforeEach
    void setUp() {
        this.tiles = Game.createTiles(1);
    }

    @Test
    void Should_Be_Compatible_With_Tile_Of_Same_Color_Or_Shape() {
        int colors = TileColor.values().length;
        int shapes = TileShape.values().length;
        int tileMatches = 0;
        for (Tile tile1 : this.tiles) {
            for (Tile tile2 : this.tiles) {
                if (tile1.isCompatible(tile2)) {
                    tileMatches += 1;
                }
            }
        }
        assertEquals(
                (colors + shapes) - 1,
                tileMatches / (colors * shapes)
        );
    }
}
