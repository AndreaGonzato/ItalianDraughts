package it.units.italiandraughts.logic;

import java.util.List;

public class Move {

    private final Piece piece;
    private final BlackTile source;
    private final BlackTile destination;
    private final List<EatenPiece> eatenPieces;

    public Move(Piece piece, BlackTile source, BlackTile destination, List<EatenPiece> eatenPieces) {
        this.piece = piece;
        this.source = source;
        this.destination = destination;
        this.eatenPieces = eatenPieces;
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
