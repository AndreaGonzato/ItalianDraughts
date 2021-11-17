package it.units.italiandraughts.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BlackTile extends Tile {

    private Piece piece;
    private final Map<String, BlackTile> neighbors = new HashMap<>();

    BlackTile(int x, int y) {
        super(x, y);
    }

    BlackTile(int x, int y, Piece piece) {
        super(x, y);
        this.piece = piece;
    }

    public Map<String, BlackTile> getNeighbors() {
        return neighbors;
    }

    void addNeighbors(Board board) {
        if (Tile.areValidCoordinatesBiPredicate.test(x - 1, y - 1)) {
            neighbors.put("topLeft", BlackTile.asBlackTile(board.getTiles()[y - 1][x - 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x + 1, y - 1)) {
            neighbors.put("topRight", BlackTile.asBlackTile(board.getTiles()[y - 1][x + 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x - 1, y + 1)) {
            neighbors.put("bottomLeft", BlackTile.asBlackTile(board.getTiles()[y + 1][x - 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x + 1, y + 1)) {
            neighbors.put("bottomRight", BlackTile.asBlackTile(board.getTiles()[y + 1][x + 1]));
        }
    }

    private Optional<String> getAdjacencyDirection(BlackTile other) {
        return neighbors.keySet().stream()
                .filter(key -> other.equals(neighbors.get(key)))
                .findFirst();
    }

    public boolean isNeighbor(BlackTile other) {
        return getAdjacencyDirection(other).isPresent();
    }

    public String getNeighborKey(BlackTile other){
        return getAdjacencyDirection(other).orElseThrow(() -> new IllegalArgumentException("You did not pass a neighbor"));
    }

    public static BlackTile asBlackTile(Tile tile) {
        if (tile instanceof BlackTile) {
            return (BlackTile) tile;
        } else {
            throw new IllegalArgumentException("A white tile cannot be cast to BlackTile");
        }
    }

    public boolean isEmpty() {
        return piece == null;
    }

    void placePiece(Piece piece) {
        this.piece = piece;
        piece.setBlackTile(this);

        if (y == piece.getPromotionRow()) {
            piece.setPieceType(PieceType.KING);
        }
    }

    Piece removePiece() {
        Piece piece = this.piece;
        piece.setBlackTile(null);
        this.piece = null;
        return piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
