package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private List<Tile> pool = new ArrayList<>();
    private List<Tile> rack = new ArrayList<>();
    private int points = 0;
    private boolean freeMove = true;

    public void changeRack(boolean removePoints) {
        if (removePoints) {
            this.removePoint(Board.MOVE_PRICE);
        }
        List<Tile> oldTiles = new ArrayList<>(this.rack);
        this.rack.clear();
        this.fillRack();
        this.pool.addAll(oldTiles);
        Collections.shuffle(this.pool);
    }

    public boolean canChangeRack() {
        return this.pool.size() >= this.rack.size();
    }

    public void fillRack() {
        while (this.rack.size() < 5 && !this.pool.isEmpty()) {
            this.rack.add(this.pool.remove(0));
        }
    }

    public void addPoint(int point) {
        this.points += point;
    }

    public void removePoint(int point) {
        this.points -= point;
    }

    public String playerName(Game game) {
        if (game.getPlayer1() == this) {
            return Game.player1Name();
        } else {
            return Game.player2Name();
        }
    }

    public void freeMovePlayed() {
        this.freeMove = false;
    }

    public void resetFreeMove() {
        this.freeMove = true;
    }

    public List<Tile> getRack() {
        return this.rack;
    }

    public void setRack(List<Tile> rack) {
        this.rack = rack;
    }

    public List<Tile> getPool() {
        return this.pool;
    }

    public void setPool(List<Tile> pool) {
        this.pool = pool;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isFreeMove() {
        return this.freeMove;
    }
}
