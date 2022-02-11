package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Piece {

    protected PieceType pieceType;
    private boolean movable;
    private BlackTile blackTile;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public Optional<EatenPiece> moveToReachableBlackTile(BlackTile landingTile) {
        if (blackTile.isNeighbor(landingTile)) {
            // simple move
            moveTo(landingTile);
            return Optional.empty();
        } else {
            // move and eat a piece
            BlackTile overTile = blackTile.getBlackTileInMiddle(landingTile);

            Piece toEat = overTile.getPiece();
            if (toEat == null){
                // TODO Tom what do you think about this if?
                throw new NullPointerException("No piece found on the BlackTile overTile");
            }
            eatNeighbor(toEat);
            return Optional.of(new EatenPiece(toEat));
        }
    }

    public void moveTo(BlackTile destination) {
        this.blackTile.removePiece();
        destination.placePiece(this);
    }

    public abstract PieceColor getPieceColor();

    public PieceType getPieceType() {
        return pieceType;
    }

    public Stream<BlackTile> getReachableNeighboringBlackTiles() {
        if (pieceType.equals(PieceType.KING)) {
            return getBlackTile().getNeighbors().values().stream();
        } else {
            return getBlackTile().getNeighbors().entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(getPieceColor().equals(PieceColor.WHITE) ? "top" : "bottom"))
                    .map(Map.Entry::getValue);
        }
    }

    public boolean canEatNeighbor(Piece neighboringPiece) {
        if (neighboringPiece == null) {
            return false;
        }
        if (neighboringPiece.getPieceColor().equals(this.getPieceColor())) {
            return false;
        }
        if (neighboringPiece.isKing() && this.isMan()) {
            return false;
        }
        String eatingDirection;
        try {
            eatingDirection = getEatingDirection(neighboringPiece);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return neighboringPiece.getBlackTile().hasNeighbor(eatingDirection)
                && getPositionAfterEatingNeighbor(neighboringPiece).isEmpty();
    }

    private void eatNeighbor(Piece neighboringPiece) {
        if (canEatNeighbor(neighboringPiece)) {
            BlackTile landingTile = getPositionAfterEatingNeighbor(neighboringPiece);
            BlackTile overBlackTile = neighboringPiece.getBlackTile();
            overBlackTile.removePiece();
            moveTo(landingTile);
        }
    }

    public BlackTile getPositionAfterEatingNeighbor(Piece neighboringPiece) {
        BlackTile overTile = neighboringPiece.getBlackTile();
        String eatingDirection = getEatingDirection(neighboringPiece);
        return overTile.getNeighbors().get(eatingDirection);
    }

    private String getEatingDirection(Piece neighboringPiece) {
        BlackTile overTile = neighboringPiece.getBlackTile();
        Optional<String> optionalDirection = this.getBlackTile().getNeighbors().entrySet().stream()
                .filter(entry -> entry.getValue().equals(overTile))
                .map(Map.Entry::getKey).findAny();
        return optionalDirection.orElseThrow(() -> new IllegalArgumentException("The piece need to be a neighbor"));
    }

    public BlackTile getBlackTile() {
        return blackTile;
    }

    public abstract int getPromotionRow();

    public void setBlackTile(BlackTile blackTile) {
        this.blackTile = blackTile;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public boolean isMan() {
        return PieceType.MAN.equals(pieceType);
    }

    public boolean isKing() {
        return PieceType.KING.equals(pieceType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!o.getClass().equals(this.getClass())) return false;

        return pieceType == this.getClass().cast(o).pieceType;
    }

    public boolean isMovable() {
        return movable;
    }

    public void updateMovable() {
        this.movable = getReachableNeighboringBlackTiles()
                .anyMatch(tile -> tile.isEmpty() || canEatNeighbor(tile.getPiece()));
    }

    @Override
    public String toString() {
        return pieceType + ", movable: " + movable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, movable, blackTile);
    }

}
