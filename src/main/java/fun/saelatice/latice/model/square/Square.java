package fun.saelatice.latice.model.square;

import fun.saelatice.latice.model.tile.Tile;

public class Square {

    private SquareType type;
    private Tile tile;

    public Square(SquareType type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }

    public Square(SquareType type) {
        this.type = type;
        this.tile = null;
    }

    @Override
    public String toString() {
        return "Square{" +
                "type=" + this.type +
                ", tile=" + this.tile +
                '}';
    }

    public SquareType getType() {
        return this.type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
