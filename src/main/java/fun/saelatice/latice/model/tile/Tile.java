package fun.saelatice.latice.model.tile;

import java.io.Serializable;

public record Tile(TileColor color, TileShape shape) implements Serializable {

}
