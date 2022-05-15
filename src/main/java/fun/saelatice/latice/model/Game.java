package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {

    public static final String PLAYER_2 = "Joueur 2";
    public static final String PLAYER_1 = "Joueur 1";
    public static final int MAX_CYCLES = 10;
    private final Player player1 = new Player();
    private final Player player2 = new Player();
    private final Board board = new Board();
    private final Random random = new Random();
    private int cycles = 0;
    private int turns = 0;
    private Player currentPlayer;

    private boolean over = false;

    public static List<Tile> createTiles(int copy) {
        List<Tile> tiles = new ArrayList<>();
        for (TileColor color : TileColor.values()) {
            for (TileShape shape : TileShape.values()) {
                for (int i = 0; i < copy; i++) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
        return tiles;
    }

    public void divideTiles() {
        List<Tile> tiles = Game.createTiles(2);
        Collections.shuffle(tiles);
        this.player1.setPool(new ArrayList<>(tiles.subList(0, tiles.size() / 2)));
        this.player2.setPool(new ArrayList<>(tiles.subList(tiles.size() / 2, tiles.size())));
    }

    public void nextPlayer() {
        this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;
        this.currentPlayer.resetFreeMove();
        this.turns += 1;
        if ((this.turns - 1) % 2 == 0 && this.turns != 1) {
            this.cycles += 1;
        }
    }

    //TODO : tester les conditions de victoire
    public void checkOver() {
        if ((this.turns - 1) % 2 != 0 && this.cycles + 1 == Game.MAX_CYCLES) {
            this.over = true;
            return;
        }
        Set<Player> players = new HashSet<>();
        players.add(this.player1);
        players.add(this.player2);
        players.forEach(player -> {
            if (player.getRack().size() + player.getPool().size() == 0) {
                this.over = true;
            }
        });
    }

    //TODO : tester méthode
    public Player getWinner() {
        int remainingTilesPlayer1 = this.player1.getRack().size() + this.player1.getPool().size();
        int remainingTilesPlayer2 = this.player2.getRack().size() + this.player2.getPool().size();
        if (remainingTilesPlayer1 < remainingTilesPlayer2) {
            return this.player1;
        } else if (remainingTilesPlayer1 > remainingTilesPlayer2) {
            return this.player2;
        }
        return null;
    }

    //TODO : tester méthode
    public Player getNextPlayer() {
        if (this.currentPlayer == this.player1) {
            return this.player2;
        } else {
            return this.player1;
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

    public int getCycles() {
        return this.cycles;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public boolean isOver() {
        return this.over;
    }

    public int getTurns() {
        return this.turns;
    }
}
