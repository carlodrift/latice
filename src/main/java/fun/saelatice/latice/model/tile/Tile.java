package fun.saelatice.latice.model.tile;

import java.io.Serializable;

public record Tile(TileColor color, TileShape shape) implements Serializable {

    public boolean isCompatible(Tile tile) {
        return (this.shape() == tile.shape() || this.color() == tile.color());
    }
}
