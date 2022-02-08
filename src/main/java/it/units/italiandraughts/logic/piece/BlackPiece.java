package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.ui.PieceColor;

public class BlackPiece extends Piece {

    private static final int PROMOTION_ROW = 7;
    private static final PieceColor PIECE_COLOR = PieceColor.BLACK;

    public BlackPiece() {
        this(PieceType.MAN);
    }

    public BlackPiece(PieceType pieceType) {
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
