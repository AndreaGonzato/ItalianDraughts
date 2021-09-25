package it.units.italiandraughts.logic;

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

    WhitePiece(BlackTile blackTile) {
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

}
