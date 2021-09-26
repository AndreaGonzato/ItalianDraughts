package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

public class BlackPiece extends Piece {

    private static final int PROMOTION_ROW = 7;
    private static final PieceColor PIECE_COLOR = PieceColor.BLACK;

    BlackPiece() {
        this(PieceType.MAN, null);
    }

    BlackPiece(PieceType pieceType) {
        this(pieceType, null);
    }

    BlackPiece(BlackTile blackTile) {
        this(PieceType.MAN, blackTile);
    }

    BlackPiece(PieceType pieceType, BlackTile blackTile) {
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
