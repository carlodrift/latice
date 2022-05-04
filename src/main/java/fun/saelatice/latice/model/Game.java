package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Color;
import fun.saelatice.latice.model.tile.Shape;
import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

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
}
