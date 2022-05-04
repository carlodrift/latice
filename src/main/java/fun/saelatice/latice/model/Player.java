package fun.saelatice.latice.model;

import fun.saelatice.latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private List<Tile> pool = new ArrayList<>();
    private List<Tile> rack = new ArrayList<>();

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

    public void changeRack() {
        if (this.pool.size() >= this.rack.size()) {
            List<Tile> oldTiles = new ArrayList<>(this.rack);
            this.rack.clear();
            this.fillRack();
            this.pool.addAll(oldTiles);
            Collections.shuffle(this.pool);
        }
    }

    private void fillRack() {
        while (this.rack.size() < 5 && !this.pool.isEmpty()) {
            this.rack.add(this.pool.remove(0));
        }
    }
}
