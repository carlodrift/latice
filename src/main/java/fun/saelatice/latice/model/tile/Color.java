package fun.saelatice.latice.model.tile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Color {
    RED,
    GREEN,
    PINK,
    BLUE,
    YELLOW,
    ORANGE;

    public static Set<Color> getList() {
        return Stream.of(Color.values()).collect(Collectors.toSet());
    }
}
