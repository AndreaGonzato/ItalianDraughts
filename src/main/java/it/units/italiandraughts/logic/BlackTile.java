package it.units.italiandraughts.logic;

import java.util.HashMap;
import java.util.Map;

public class BlackTile extends Tile {

    private Piece piece;
    private final Map<String, BlackTile> neighbors = new HashMap<>();

    BlackTile(int x, int y){
        super(x, y);
    }

    BlackTile(int x, int y, Piece piece) {
        super(x, y);
        this.piece = piece;
    }

    public Map<String, BlackTile> getNeighbors() {
        return neighbors;
    }

    void addNeighbors(Tile[][] tiles) {
        if (Tile.areValidCoordinates.test(new int[] {x - 1, y - 1})) {
            neighbors.put("topLeft", BlackTile.asBlackTile(tiles[y - 1][x - 1]));
        }
        if (Tile.areValidCoordinates.test(new int[] {x + 1, y - 1})) {
            neighbors.put("topRight", BlackTile.asBlackTile(tiles[y - 1][x + 1]));
        }
        if (Tile.areValidCoordinates.test(new int[] {x - 1, y + 1})) {
            neighbors.put("bottomLeft", BlackTile.asBlackTile(tiles[y + 1][x - 1]));
        }
        if (Tile.areValidCoordinates.test(new int[] {x + 1, y + 1})) {
            neighbors.put("bottomRight", BlackTile.asBlackTile(tiles[y + 1][x + 1]));
        }
    }

    public static BlackTile asBlackTile(Tile tile){
        return (BlackTile) tile;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    void placePiece(Piece piece) {
        this.piece = piece;
    }

    void removePiece() {
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }
}
