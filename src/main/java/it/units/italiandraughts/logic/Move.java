package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.EatenPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import it.units.italiandraughts.ui.PieceColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public class Move {

    private final Piece piece;
    private final BlackTile source;
    private final BlackTile destination;
    private final List<EatenPiece> eatenPieces;
    private final List<BlackTile> steps;
    private boolean hasPromoted;

    public Move(Piece piece, List<BlackTile> steps) {
        checkPreconditions(piece, steps);

        this.piece = piece;
        this.source = steps.get(0);
        this.destination = steps.get(steps.size() - 1);
        this.steps = steps;
        this.eatenPieces = new ArrayList<>();
    }

    private void checkPreconditions(Piece piece, List<BlackTile> steps) {
        if (piece.isMan()) {
            BiFunction<Tile, Tile, String> movingDirection = Tile::getDirection;
            String validDirection = piece.getPieceColor().equals(PieceColor.WHITE) ? "top" : "bottom";
            String invalidDirection = piece.getPieceColor().equals(PieceColor.WHITE) ? "bottom" : "top";
            for (int i = 1; i < steps.size(); i++) {
                if (movingDirection.apply(steps.get(i - 1), steps.get(i)).equals(invalidDirection)) {
                    throw new IllegalArgumentException("A %s man can only move to the %s."
                            .formatted(piece.getPieceColor().toString(), validDirection));
                }
            }
        }

        BiFunction<Tile, Tile, Integer> movingStepDistance = Tile::calculateDistance;
        for (int i = 1; i < steps.size(); i++) {
            if (movingStepDistance.apply(steps.get(i - 1), steps.get(i)) > 2) {
                throw new IllegalArgumentException("Two steps cannot be distant more than 2.");
            }
        }

    }

    void make() {
        boolean wasMan = piece.isMan();
        for (int i = 1; i < steps.size(); i++) {
            final BlackTile landingTile = steps.get(i);
            Optional<EatenPiece> eatenPieceOptional = piece.moveToReachableBlackTile(landingTile);
            eatenPieceOptional.ifPresent(eatenPieces::add);
        }
        boolean isKing = piece.isKing();
        hasPromoted = wasMan && isKing;
    }

    void undo() {
        eatenPieces.forEach(EatenPiece::restore);
        if (hasPromoted) {
            piece.setPieceType(PieceType.MAN);
        }
        piece.moveTo(source);
    }

    public boolean hasPromoted() {
        return hasPromoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return hasPromoted == move.hasPromoted && piece.equals(move.piece) && source.equals(move.source) && destination.equals(move.destination) && eatenPieces.equals(move.eatenPieces) && steps.equals(move.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, source, destination, eatenPieces, steps, hasPromoted);
    }

}
