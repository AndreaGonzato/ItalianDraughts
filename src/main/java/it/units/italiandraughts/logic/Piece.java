package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Piece {
    private final PieceColor pieceColor;
    private PieceType pieceType;
    private boolean movable;
    private BlackTile blackTile;

    public Piece(PieceColor pieceColor, BlackTile blackTile) {
        this(pieceColor, PieceType.MAN, blackTile);
    }

    public Piece(PieceColor pieceColor, PieceType pieceType, BlackTile blackTile) {
        this.blackTile = blackTile;
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        movable = false;
        blackTile.placePiece(this);
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Stream<BlackTile> getReachableNeighborsBlackTiles() {
        if (pieceType.equals(PieceType.KING)) {
            return getBlackTile().getNeighbors().values().stream();
        } else {
            return getBlackTile().getNeighbors().entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(getPieceColor().equals(PieceColor.WHITE) ? "top" : "bottom"))
                    .map(Map.Entry::getValue);
        }
    }

    public boolean canEatNeighbor(Piece otherPiece) {
        if (otherPiece == null) {
            return false;
        }
        if (otherPiece.getPieceColor().equals(this.getPieceColor())) {
            return false;
        }
        BlackTile landingTile = getPositionAfterEating(otherPiece);
        if (landingTile != null) {
            return landingTile.isEmpty();
        }
        return false;
    }

    BlackTile getPositionAfterEating(Piece otherPiece) {
        Optional<String> optionalDirection = this.getBlackTile().getNeighbors().entrySet().stream()
                .filter(entry -> entry.getValue().equals(otherPiece.getBlackTile()))
                .map(Map.Entry::getKey).findAny();
        if (optionalDirection.isPresent()) {
            String eatingDirection = optionalDirection.get();
            return otherPiece.getBlackTile().getNeighbors().get(eatingDirection);
        }
        return null;
    }

    public BlackTile getBlackTile() {
        return blackTile;
    }

    public void setBlackTile(BlackTile blackTile) {
        this.blackTile = blackTile;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

        return pieceColor == piece.pieceColor && pieceType == piece.pieceType;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    @Override
    public String toString() {
        return pieceColor + ", " + pieceType + ", movable: " + movable;
    }

    @Override
    public int hashCode() {
        return pieceColor.hashCode();
    }
}
