package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;

public class WhitePiece extends Piece {

    private static final int PROMOTION_ROW = 0;
    private static final PieceColor PIECE_COLOR = PieceColor.WHITE;

    WhitePiece() {
        this(PieceType.MAN, null);
    }

    WhitePiece(PieceType pieceType) {
        this(pieceType, null);
    }

    public WhitePiece(BlackTile blackTile) {
        this(PieceType.MAN, blackTile);
    }

    WhitePiece(PieceType pieceType, BlackTile blackTile) {
        super(pieceType, blackTile);
    }

    @Override
    public int getPromotionRow() {
        return PROMOTION_ROW;
    }

    @Override
    public PieceColor getPieceColor() {
        return PIECE_COLOR;
    }

    @Override
    public String toString() {
        return PIECE_COLOR + ", " + super.toString();
    }

}
