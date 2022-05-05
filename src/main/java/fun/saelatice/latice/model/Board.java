package fun.saelatice.latice.model;

import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Square> squares = new HashMap<>();

    public void init() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i == j || i + j == 8) && i != 3 && i != 5) {
                    this.squares.put(new Position(i, j), new Square(SquareType.STAR));
                } else {
                    this.squares.put(new Position(i, j), new Square(SquareType.NORMAL));
                }
            }
        }
        this.squares.put(new Position(4, 4), new Square(SquareType.MOON));
        this.squares.put(new Position(0, 4), new Square(SquareType.STAR));
        this.squares.put(new Position(4, 0), new Square(SquareType.STAR));
        this.squares.put(new Position(4, 8), new Square(SquareType.STAR));
        this.squares.put(new Position(8, 4), new Square(SquareType.STAR));
    }

    public Map<Position, Square> getSquares() {
        return this.squares;
    }
}
