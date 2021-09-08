package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Piece {
    private final PieceColor pieceColor;
    private PieceType pieceType;
    private boolean movable;
    private BlackTile tile;

    public Piece(PieceColor pieceColor, BlackTile tile) {
        this(pieceColor, PieceType.MAN, tile);
    }

    public Piece(PieceColor pieceColor, PieceType pieceType, BlackTile tile) {
        this.tile = tile;
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        movable = false;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Stream<BlackTile> getNeighborsThisPieceCanMoveTowards() {
        if (pieceType.equals(PieceType.KING)) {
            return getTile().getNeighbors().values().stream();
        } else {
            return getTile().getNeighbors().entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(getPieceColor().equals(PieceColor.WHITE) ? "top" : "bottom"))
                    .map(Map.Entry::getValue);
        }
    }

    public boolean canEatNeighbor(Piece otherPiece) {
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
        Optional<String> optionalDirection = this.getTile().getNeighbors().entrySet().stream()
                .filter(entry -> entry.getValue().equals(otherPiece.getTile()))
                .map(Map.Entry::getKey).findAny();
        if (optionalDirection.isPresent()) {
            String eatingDirection = optionalDirection.get();
            return otherPiece.getTile().getNeighbors().get(eatingDirection);
        }
        return null;
    }

    public BlackTile getTile() {
        return tile;
    }

    public void setTile(BlackTile tile) {
        this.tile = tile;
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
