package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.EatenPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.tile.BlackTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Move {

    private final Piece piece;
    private final BlackTile source;
    private final BlackTile destination;
    private final List<EatenPiece> eatenPieces;
    private final List<BlackTile> steps;
    private boolean hasPromoted;

    public Move(Piece piece, List<BlackTile> steps) {
        // TODO control that a white man piece can not move down

        this.piece = piece;
        this.source = piece.getBlackTile();
        this.destination = steps.get(steps.size() - 1);
        this.steps = steps;
        this.eatenPieces = new ArrayList<>();
    }

    public void make() {
        for (int i = 1; i < steps.size(); i++) {
            final BlackTile landingTile = steps.get(i);
            boolean wasMan = piece.isMan();
            Optional<EatenPiece> eatenPieceOptional = piece.moveToReachableBlackTile(landingTile);
            boolean isKing = piece.isKing();
            eatenPieceOptional.ifPresent(eatenPieces::add);
            hasPromoted = wasMan && isKing;
        }
    }

    public void undo() {
        eatenPieces.forEach(EatenPiece::restore);
        if (hasPromoted) {
            piece.setPieceType(PieceType.MAN);
        }
        piece.moveTo(source);

    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPromoted() {
        return hasPromoted;
    }

    public BlackTile getSource() {
        return source;
    }

    public BlackTile getDestination() {
        return destination;
    }

    public List<EatenPiece> getEatenPieces() {
        return eatenPieces;
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
