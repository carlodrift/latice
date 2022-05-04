package fun.saelatice.latice.model.tile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Shape {
    MARIO,
    PEACH,
    BOWSER,
    TOAD,
    LUIGI,
    YOSHI;

    public static Set<Shape> getList() {
        return Stream.of(Shape.values()).collect(Collectors.toSet());
    }
}
