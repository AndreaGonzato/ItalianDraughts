package it.units.italiandraughts.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlackTile blackTile = (BlackTile) o;
        return Objects.equals(piece, blackTile.piece) && Objects.equals(neighbors, blackTile.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), piece);
    }

    public Map<String, BlackTile> getNeighbors() {
        return neighbors;
    }

    void addNeighbors(Tile[][] tiles) {
        if (Tile.areValidCoordinatesOfTileBiPredicate.test(x - 1, y - 1)) {
            neighbors.put("topLeft", BlackTile.asBlackTile(tiles[y - 1][x - 1]));
        }
        if (Tile.areValidCoordinatesOfTileBiPredicate.test(x + 1, y - 1)) {
            neighbors.put("topRight", BlackTile.asBlackTile(tiles[y - 1][x + 1]));
        }
        if (Tile.areValidCoordinatesOfTileBiPredicate.test(x - 1, y + 1)) {
            neighbors.put("bottomLeft", BlackTile.asBlackTile(tiles[y + 1][x - 1]));
        }
        if (Tile.areValidCoordinatesOfTileBiPredicate.test(x + 1, y + 1)) {
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
        piece.setTile(this);
    }

    void removePiece() {
        piece.setTile(null);
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }
}
