package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

import java.util.Map;
import java.util.Optional;

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

    public boolean canEat(Piece otherPiece) {
        if (otherPiece.getPieceColor().equals(this.getPieceColor())) {
            return false;
        }
        BlackTile thisTile = tile;
        BlackTile otherTile = otherPiece.getTile();
        Optional<String> optionalDirection = thisTile.getNeighbors().entrySet().stream()
                .filter(entry -> entry.getValue().equals(otherTile))
                .map(Map.Entry::getKey).findAny();
        if (optionalDirection.isPresent()) {
            String eatingDirection = optionalDirection.get();
            BlackTile landingTile = otherTile.getNeighbors().get(eatingDirection);
            if (landingTile != null) {
                return landingTile.isEmpty();
            }
        }
        return false;
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
