package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.ui.PieceColor;

public class WhitePiece extends Piece {

    private static final int PROMOTION_ROW = 0;
    private static final PieceColor PIECE_COLOR = PieceColor.WHITE;

    public WhitePiece() {
        this(PieceType.MAN);
    }

    public WhitePiece(PieceType pieceType) {
        super(pieceType);
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
