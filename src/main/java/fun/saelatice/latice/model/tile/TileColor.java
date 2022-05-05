package fun.saelatice.latice.model.tile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TileColor {
    RED,
    GREEN,
    PINK,
    BLUE,
    YELLOW,
    ORANGE;

    public static Set<TileColor> getList() {
        return Stream.of(TileColor.values()).collect(Collectors.toSet());
    }
}
