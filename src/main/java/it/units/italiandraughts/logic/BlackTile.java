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

    public boolean isNeighbor(BlackTile otherBlackTile) {
        Optional<String> eatingDirection = neighbors
                .keySet()
                .stream()
                .filter(key -> otherBlackTile.equals(neighbors.get(key)))
                .findFirst();
        return eatingDirection.isPresent();
    }

    public String getNeighborKey(BlackTile neighborBlackTile){
        if (isNeighbor(neighborBlackTile)){
            return neighbors
                    .keySet()
                    .stream()
                    .filter(key -> neighborBlackTile.equals(neighbors.get(key)))
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        }else {
            throw new IllegalArgumentException("you didn't pass a neighbor");
        }
    }

    public static BlackTile asBlackTile(Tile tile) {
        return (BlackTile) tile;
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
