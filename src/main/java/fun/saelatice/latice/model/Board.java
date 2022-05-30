package fun.saelatice.latice.model;

import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;
import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    public static final int DOUBLE_POINTS = 1;
    public static final int TREFOIL_POINTS = 2;
    public static final int LATICE_POINTS = 4;
    public static final int STAR_POINTS = 2;
    public static final int MOVE_PRICE = 2;
    private final Map<Position, Square> squares = new HashMap<>();

    public void init() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.squares.put(new Position(i, j), new Square(SquareType.NORMAL));
            }
        }
        this.moonSquaresPosition().forEach(position ->
                this.squares.put(position, new Square(SquareType.MOON))
        );
        this.starSquaresPosition().forEach(position ->
                this.squares.put(position, new Square(SquareType.STAR))
        );
    }

    public Set<Position> starSquaresPosition() {
        Set<Position> starSquaresPosition = new HashSet<>();
        starSquaresPosition.add(new Position(0, 4));
        starSquaresPosition.add(new Position(4, 0));
        starSquaresPosition.add(new Position(4, 8));
        starSquaresPosition.add(new Position(8, 4));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i == j || i + j == 8) && i != 3 && i != 5) {
                    starSquaresPosition.add(new Position(i, j));
                }
            }
        }
        starSquaresPosition.removeAll(this.moonSquaresPosition());
        return starSquaresPosition;
    }

    public Set<Position> moonSquaresPosition() {
        Set<Position> moonSquaresPosition = new HashSet<>();
        moonSquaresPosition.add(new Position(4, 4));
        return moonSquaresPosition;
    }

    public int pointsAt(Position position) {
        int points = 0;
        Square square = this.squares.get(position);
        if (square.getType() == SquareType.STAR) {
            points += Board.STAR_POINTS;
        }
        int tileMatches = this.aroundTiles(position.x(), position.y()).size();
        if (tileMatches == 2) {
            points += Board.DOUBLE_POINTS;
        } else if (tileMatches == 3) {
            points += Board.TREFOIL_POINTS;
        } else if (tileMatches == 4) {
            points += Board.LATICE_POINTS;
        }
        return points;
    }

    public List<Tile> aroundTiles(int x, int y) {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(this.tileAbove(x, y));
        tiles.add(this.tileBelow(x, y));
        tiles.add(this.tileLeft(x, y));
        tiles.add(this.tileRight(x, y));
        tiles.remove(null);
        tiles.remove(null);
        tiles.remove(null);
        tiles.remove(null);
        return tiles;
    }

    public boolean canPlayHere(Position position, Tile tile) {
        Square square = this.squares.get(position);
        if (square.getTile() != null) {
            return false;
        }
        int tileMatches = 0;
        for (Tile tileAround : this.aroundTiles(position.x(), position.y())) {
            if (!tileAround.isCompatible(tile)) {
                return false;
            } else {
                tileMatches += 1;
            }
        }
        return tileMatches != 0 || square.getType() == SquareType.MOON;
    }

    public void playTile(Position position, Tile tile, Player player) {
        if (!player.isFreeMove()) {
            player.removePoint(Board.MOVE_PRICE);
        }
        this.putTile(position, tile);
        player.addPoint(this.pointsAt(position));
        player.freeMovePlayed();
        player.getRack().remove(tile);
        player.fillRack();
    }

    public void putTile(Position position, Tile tile) {
        this.squares.get(position).setTile(tile);
    }

    private Tile tileAbove(int x, int y) {
        if (y == 0) {
            return null;
        }
        return this.squares.get(new Position(x, y - 1)).getTile();
    }

    private Tile tileBelow(int x, int y) {
        if (y == 8) {
            return null;
        }
        return this.squares.get(new Position(x, y + 1)).getTile();
    }

    private Tile tileLeft(int x, int y) {
        if (x == 0) {
            return null;
        }
        return this.squares.get(new Position(x - 1, y)).getTile();
    }

    private Tile tileRight(int x, int y) {
        if (x == 8) {
            return null;
        }
        return this.squares.get(new Position(x + 1, y)).getTile();
    }

    public Map<Position, Square> getSquares() {
        return this.squares;
    }
}
