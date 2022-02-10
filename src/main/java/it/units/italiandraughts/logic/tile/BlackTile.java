package it.units.italiandraughts.logic.tile;

import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;

import java.util.*;

public class BlackTile extends Tile {

    private Piece piece;
    private final Map<String, BlackTile> neighbors = new HashMap<>();

    public BlackTile(int x, int y) {
        super(x, y);
    }

    public Map<String, BlackTile> getNeighbors() {
        return neighbors;
    }

    public void populateNeighborsFromBoard() {
        Board board = Board.getBoard();
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

    public void placePiece(Piece piece) {
        this.piece = piece;
        piece.setBlackTile(this);

        if (y == piece.getPromotionRow()) {
            piece.setPieceType(PieceType.KING);
        }
    }

    public Piece removePiece() {
        Piece piece = this.piece;
        if (piece == null) {
            return null;
        }
        piece.setBlackTile(null);
        this.piece = null;
        return piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasNeighbor(String direction) {
        return neighbors.get(direction) != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlackTile blackTile)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(piece, blackTile.piece);
    }

}
