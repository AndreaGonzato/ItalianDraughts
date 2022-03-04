package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class PlayerTest {

    @Test
    void checkPiecesOfPlayer1() {
        Board board = Board.reset();
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
    void checkPiecesOfPlayer2() {
        Board board = Board.reset();
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

    @Test
    void checkMovablePiecesOnNewGame() {
        Board board = Board.reset();
        board.initPieces();
        Player player = new Player("", PieceColor.WHITE);
        player.updateMovablePieces();
        Piece piece1 = BlackTile.asBlackTile(board.getTiles()[5][1]).getPiece();
        Piece piece2 = BlackTile.asBlackTile(board.getTiles()[5][3]).getPiece();
        Piece piece3 = BlackTile.asBlackTile(board.getTiles()[5][5]).getPiece();
        Piece piece4 = BlackTile.asBlackTile(board.getTiles()[5][7]).getPiece();
        Piece piece5 = BlackTile.asBlackTile(board.getTiles()[6][0]).getPiece();
        Piece piece6 = BlackTile.asBlackTile(board.getTiles()[6][2]).getPiece();
        Piece piece7 = BlackTile.asBlackTile(board.getTiles()[6][4]).getPiece();
        Piece piece8 = BlackTile.asBlackTile(board.getTiles()[6][6]).getPiece();
        Piece piece9 = BlackTile.asBlackTile(board.getTiles()[7][1]).getPiece();
        Piece piece10 = BlackTile.asBlackTile(board.getTiles()[7][3]).getPiece();
        Piece piece11 = BlackTile.asBlackTile(board.getTiles()[7][5]).getPiece();
        Piece piece12 = BlackTile.asBlackTile(board.getTiles()[7][7]).getPiece();

        Assertions.assertTrue(
                piece1.isMovable() && piece2.isMovable() && piece3.isMovable() && piece4.isMovable() &&
                        !(piece5.isMovable() || piece6.isMovable() || piece7.isMovable() || piece8.isMovable()
                                || piece9.isMovable() || piece10.isMovable() || piece11.isMovable()
                                || piece12.isMovable())
        );
    }

    @Test
    void checkMovablePiecesInTestingScenario() {
        Board board = Board.reset();
        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[0][0]);
        Piece piece1 = new BlackPiece();
        blackTile1.placePiece(piece1);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[0][4]);
        Piece piece2 = new BlackPiece();
        blackTile2.placePiece(piece2);
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[1][3]);
        Piece piece3 = new WhitePiece();
        blackTile3.placePiece(piece3);
        BlackTile blackTile4 = BlackTile.asBlackTile(board.getTiles()[1][5]);
        Piece piece4 = new WhitePiece();
        blackTile4.placePiece(piece4);
        BlackTile blackTile5 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        Piece piece5 = new BlackPiece();
        blackTile5.placePiece(piece5);

        Player player = new Player("", PieceColor.BLACK);
        player.updateMovablePieces();
        Assertions.assertTrue(
                !piece1.isMovable() && piece2.isMovable() && piece5.isMovable()
        );
    }

    @Test
    void checkNumberOfMovablePiecesOnNewGame() {
        Board board = Board.reset();
        board.initPieces();
        Player player = new Player("", PieceColor.WHITE);
        player.updateMovablePieces();
        int expected = 4;
        int actual = player.countMovablePieces();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkNumberOfMovablePiecesInTestingScenario() {
        Board board = Board.reset();
        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[0][0]);
        Piece piece1 = new BlackPiece();
        blackTile1.placePiece(piece1);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[0][4]);
        Piece piece2 = new BlackPiece();
        blackTile2.placePiece(piece2);
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[1][3]);
        Piece piece3 = new WhitePiece();
        blackTile3.placePiece(piece3);
        BlackTile blackTile4 = BlackTile.asBlackTile(board.getTiles()[1][5]);
        Piece piece4 = new WhitePiece();
        blackTile4.placePiece(piece4);
        BlackTile blackTile5 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        Piece piece5 = new BlackPiece();
        blackTile5.placePiece(piece5);

        Player player = new Player("", PieceColor.BLACK);
        player.updateMovablePieces();
        int expected = 2;
        int actual = player.countMovablePieces();
        Assertions.assertEquals(expected, actual);
    }

}