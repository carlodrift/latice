package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Color;
import fun.saelatice.latice.model.tile.Shape;
import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Player player1 = new Player();
    private final Player player2 = new Player();

    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        Color.getList().forEach(color -> {
            Shape.getList().forEach(shape -> {
                for (int i = 0; i < 2; i++) {
                    tiles.add(new Tile(color, shape));
                }
            });
        });
        return tiles;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
