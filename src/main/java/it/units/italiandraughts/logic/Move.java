package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalButtonClickException;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private final Piece piece;
    private final BlackTile source;
    private final BlackTile destination;
    private final List<EatenPiece> eatenPieces;
    private final List<BlackTile> steps;

    public Move(Piece piece, BlackTile source, BlackTile destination, List<BlackTile> steps) {
        this.piece = piece;
        this.source = source;
        this.destination = destination;
        this.steps = steps;
        this.eatenPieces = new ArrayList<>();

    }

    public void doIt(){
        for (int i = 1; i < steps.size(); i++) {
            final BlackTile landingTile = steps.get(i);
            piece.moveToReachableNeighboringBlackTile(landingTile, eatenPieces);
        }
    }

    public void undoIt(){
        eatenPieces.forEach(EatenPiece::restore);
        if (piece.getBlackTile().getY() == piece.getPromotionRow()) {
            piece.setPieceType(PieceType.MAN);
        }
        movePiece(piece, source);
    }

    public void movePiece(Piece piece, BlackTile destination) {
        BlackTile source = piece.getBlackTile();
        source.removePiece();
        destination.placePiece(piece);
    }

    public Piece getPiece() {
        return piece;
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
}
