package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    public static final String PLAYER_2 = "Joueur 2";
    public static final String PLAYER_1 = "Joueur 1";
    private final Player player1 = new Player();
    private final Player player2 = new Player();
    private final Board board = new Board();
    private final int cycles = 0;
    private final Random random = new Random();
    private Player currentPlayer;

    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
        for (TileColor color : TileColor.values()) {
            for (TileShape shape : TileShape.values()) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
        return tiles;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void divideTiles() {
        List<Tile> tiles = this.createTiles();
        Collections.shuffle(tiles);
        this.player1.setPool(new ArrayList<>(tiles.subList(0, tiles.size() / 2)));
        this.player2.setPool(new ArrayList<>(tiles.subList(tiles.size() / 2, tiles.size())));
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void nextPlayer() {
        this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;
    }

    public String getPlayer(boolean next) {
        if (this.currentPlayer == this.player1) {
            return next ? Game.PLAYER_2 : Game.PLAYER_1;
        } else {
            return next ? Game.PLAYER_1 : Game.PLAYER_2;
        }
    }

    public void start() {
        this.board.init();
        this.divideTiles();
        this.player1.changeRack();
        this.player2.changeRack();
        this.currentPlayer = this.random.nextBoolean() ? this.player1 : this.player2;
    }

    public Board getBoard() {
        return this.board;
    }
}
