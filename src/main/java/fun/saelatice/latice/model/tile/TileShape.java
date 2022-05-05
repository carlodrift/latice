package fun.saelatice.latice.model.tile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TileShape {
    MARIO,
    PEACH,
    BOWSER,
    TOAD,
    LUIGI,
    YOSHI;

    public static Set<TileShape> getList() {
        return Stream.of(TileShape.values()).collect(Collectors.toSet());
    }
}
