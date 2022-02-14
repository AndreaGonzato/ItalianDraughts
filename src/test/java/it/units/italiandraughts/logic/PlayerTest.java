package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.Objects;

@ExtendWith(ApplicationExtension.class)
public class PlayerTest {

    @Test
    void getPiecesOfPlayer1() {
        Board board = Board.getBoard();
        board.initPieces();
        Player player = new Player("", PieceColor.WHITE);
        Piece[] actualPieces = new Piece[12];
        actualPieces[0] = BlackTile.asBlackTile(board.getTiles()[7][1]).getPiece();
        actualPieces[1] = BlackTile.asBlackTile(board.getTiles()[7][3]).getPiece();
        actualPieces[2] = BlackTile.asBlackTile(board.getTiles()[7][5]).getPiece();
        actualPieces[3] = BlackTile.asBlackTile(board.getTiles()[7][7]).getPiece();
        actualPieces[4] = BlackTile.asBlackTile(board.getTiles()[6][0]).getPiece();
        actualPieces[5] = BlackTile.asBlackTile(board.getTiles()[6][2]).getPiece();
        actualPieces[6] = BlackTile.asBlackTile(board.getTiles()[6][4]).getPiece();
        actualPieces[7] = BlackTile.asBlackTile(board.getTiles()[6][6]).getPiece();
        actualPieces[8] = BlackTile.asBlackTile(board.getTiles()[5][1]).getPiece();
        actualPieces[9] = BlackTile.asBlackTile(board.getTiles()[5][3]).getPiece();
        actualPieces[10] = BlackTile.asBlackTile(board.getTiles()[5][5]).getPiece();
        actualPieces[11] = BlackTile.asBlackTile(board.getTiles()[5][7]).getPiece();
        Piece[] expectedPieces = player.getPieces().toArray(new Piece[0]);
        Assertions.assertTrue(Objects.deepEquals(expectedPieces, actualPieces));
    }

    @Test
    void getPiecesOfPlayer2() {
        Board board = Board.getBoard();
        board.initPieces();
        Player player = new Player("", PieceColor.BLACK);
        Piece[] actualPieces = new Piece[12];
        actualPieces[0] = BlackTile.asBlackTile(board.getTiles()[0][0]).getPiece();
        actualPieces[1] = BlackTile.asBlackTile(board.getTiles()[0][2]).getPiece();
        actualPieces[2] = BlackTile.asBlackTile(board.getTiles()[0][4]).getPiece();
        actualPieces[3] = BlackTile.asBlackTile(board.getTiles()[0][6]).getPiece();
        actualPieces[4] = BlackTile.asBlackTile(board.getTiles()[1][1]).getPiece();
        actualPieces[5] = BlackTile.asBlackTile(board.getTiles()[1][3]).getPiece();
        actualPieces[6] = BlackTile.asBlackTile(board.getTiles()[1][5]).getPiece();
        actualPieces[7] = BlackTile.asBlackTile(board.getTiles()[1][7]).getPiece();
        actualPieces[8] = BlackTile.asBlackTile(board.getTiles()[2][0]).getPiece();
        actualPieces[9] = BlackTile.asBlackTile(board.getTiles()[2][2]).getPiece();
        actualPieces[10] = BlackTile.asBlackTile(board.getTiles()[2][4]).getPiece();
        actualPieces[11] = BlackTile.asBlackTile(board.getTiles()[2][6]).getPiece();
        Piece[] expectedPieces = player.getPieces().toArray(new Piece[0]);
        Assertions.assertTrue(Objects.deepEquals(expectedPieces, actualPieces));
    }

}