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
            blackTile.removePiece();
            landingTile.placePiece(this);
            return Optional.empty();
        } else {
            // move and eat a piece
            BlackTile overTile = blackTile.getNeighbors().values().stream()
                    .filter(neighbor -> neighbor.getNeighbors().values().stream()
                            .anyMatch(blackTile -> blackTile.equals(landingTile)))
                    .findAny().orElseThrow();

            Optional<EatenPiece> eatenPieceOptional = Optional.of(new EatenPiece(overTile));
            eatNeighbor(overTile.getPiece());
            return eatenPieceOptional;
        }
    }

    public void move(BlackTile destination) {
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
        BlackTile landingTile;
        try {
            landingTile = getPositionAfterEatingNeighbor(neighboringPiece);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return landingTile.isEmpty();
    }

    public void eatNeighbor(Piece neighboringPiece) {
        if (canEatNeighbor(neighboringPiece)) {
            BlackTile landingTile = getPositionAfterEatingNeighbor(neighboringPiece);
            BlackTile sourceBlackTile = this.getBlackTile();
            BlackTile overBlackTile = neighboringPiece.getBlackTile();
            sourceBlackTile.removePiece();
            overBlackTile.removePiece();
            landingTile.placePiece(this);
        }
    }

    public BlackTile getPositionAfterEatingNeighbor(Piece neighboringPiece) {
        Optional<String> optionalDirection = this.getBlackTile().getNeighbors().entrySet().stream()
                .filter(entry -> entry.getValue().equals(neighboringPiece.getBlackTile()))
                .map(Map.Entry::getKey).findAny();

        String eatingDirection = optionalDirection.orElseThrow(() -> new IllegalArgumentException("The piece need to be a neighbor"));
        return neighboringPiece.getBlackTile().getNeighbors().get(eatingDirection);
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
