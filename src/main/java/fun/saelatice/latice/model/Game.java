package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Player player1 = new Player();
    private final Player player2 = new Player();
    private final Board board = new Board();

    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        TileColor.getList().forEach(color -> {
            TileShape.getList().forEach(shape -> {
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

    public void divideTiles() {
        List<Tile> tiles = this.createTiles();
        Collections.shuffle(tiles);
        this.player1.setPool(new ArrayList<>(tiles.subList(0, tiles.size() / 2)));
        this.player2.setPool(new ArrayList<>(tiles.subList(tiles.size() / 2, tiles.size())));
    }
}
