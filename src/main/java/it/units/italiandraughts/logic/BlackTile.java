package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

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
        if (Tile.areValidCoordinatesBiPredicate.test(x - 1, y - 1)) {
            neighbors.put("topLeft", BlackTile.asBlackTile(tiles[y - 1][x - 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x + 1, y - 1)) {
            neighbors.put("topRight", BlackTile.asBlackTile(tiles[y - 1][x + 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x - 1, y + 1)) {
            neighbors.put("bottomLeft", BlackTile.asBlackTile(tiles[y + 1][x - 1]));
        }
        if (Tile.areValidCoordinatesBiPredicate.test(x + 1, y + 1)) {
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

        if (y == 0 && piece.getPieceColor().equals(PieceColor.WHITE)){
            piece.setPieceType(PieceType.KING);
        }

        if (y == 7 && piece.getPieceColor().equals(PieceColor.BLACK)){
            piece.setPieceType(PieceType.KING);
        }

    }

    void removePiece() {
        piece.setTile(null);
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }
}
