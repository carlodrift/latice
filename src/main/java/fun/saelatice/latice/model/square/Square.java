package fun.saelatice.latice.model.square;

import fun.saelatice.latice.model.tile.Tile;

public class Square {

    private final SquareType type;
    private Tile tile;

    public Square(SquareType type) {
        this.type = type;
        this.tile = null;
    }

    public SquareType getType() {
        return this.type;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
