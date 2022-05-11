package fun.saelatice.latice.model;

import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;
import fun.saelatice.latice.model.tile.Tile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    public static final int DOUBLE_POINTS = 1;
    public static final int TREFOIL_POINTS = 2;
    public static final int LATICE_POINTS = 4;
    public static final int STAR_POINTS = 2;
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

    public int getPointsAt(Position position) {
        int points = 0;
        Square square = this.squares.get(position);
        if (square.getType() == SquareType.STAR) {
            points += Board.STAR_POINTS;
        }
        int tileMatches = this.getAroundTiles(position.x(), position.y()).size();
        if (tileMatches == 2) {
            points += Board.DOUBLE_POINTS;
        } else if (tileMatches == 3) {
            points += Board.TREFOIL_POINTS;
        } else if (tileMatches == 4) {
            points += Board.LATICE_POINTS;
        }
        return points;
    }

    private Tile getTileAbove(int x, int y) {
        if (y == 0) {
            return null;
        }
        return this.squares.get(new Position(x, y - 1)).getTile();
    }

    private Tile getTileBelow(int x, int y) {
        if (y == 8) {
            return null;
        }
        return this.squares.get(new Position(x, y + 1)).getTile();
    }

    private Tile getTileLeft(int x, int y) {
        if (x == 0) {
            return null;
        }
        return this.squares.get(new Position(x - 1, y)).getTile();
    }

    private Tile getTileRight(int x, int y) {
        if (x == 8) {
            return null;
        }
        return this.squares.get(new Position(x + 1, y)).getTile();
    }

    public Set<Tile> getAroundTiles(int x, int y) {
        Set<Tile> tiles = new HashSet<>();
        tiles.add(this.getTileAbove(x, y));
        tiles.add(this.getTileBelow(x, y));
        tiles.add(this.getTileLeft(x, y));
        tiles.add(this.getTileRight(x, y));
        tiles.remove(null);
        return tiles;
    }

    public Map<Position, Square> getSquares() {
        return this.squares;
    }

    public void setTile(Position position, Tile tile) {
        this.squares.get(position).setTile(tile);
    }
}
