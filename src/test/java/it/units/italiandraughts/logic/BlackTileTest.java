package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class BlackTileTest {

    @Test
    void getPiece() {
        BlackTile blackTile = new BlackTile(0, 0);

        Piece expectedPiece = new Piece(PieceColor.WHITE, blackTile);
        Piece actualPiece = blackTile.getPiece();

        Assertions.assertEquals(expectedPiece, actualPiece);

    }

    @Test
    void getNeighborsX0Y0() {
        BlackTile blackTile = new BlackTile(0, 0);
        Board board = new Board();

        blackTile.addNeighbors(board);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("bottomRight", new BlackTile(1, 1));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void getNeighborsX1Y1() {
        BlackTile blackTile = new BlackTile(1, 1);
        Board board = new Board();

        blackTile.addNeighbors(board);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(0, 0));
        expectedMap.put("topRight", new BlackTile(2, 0));
        expectedMap.put("bottomLeft", new BlackTile(0, 2));
        expectedMap.put("bottomRight", new BlackTile(2, 2));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void getNeighborsX7Y7() {
        BlackTile blackTile = new BlackTile(7, 7);
        Board board = new Board();

        blackTile.addNeighbors(board);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(6, 6));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void isEmptyOnEmptyBlackTile() {
        BlackTile blackTile = new BlackTile(0, 0);

        Assertions.assertEquals(true, blackTile.isEmpty());

    }

    @Test
    void isEmptyOnFullBlackTile() {
        BlackTile blackTile = new BlackTile(0, 0);
        blackTile.placePiece(new Piece(PieceColor.BLACK));

        Assertions.assertEquals(false, blackTile.isEmpty());

    }

    @Test
    void asBlackTile() {
        Tile tile = new BlackTile(0, 0);
        BlackTile actualBlackTile = BlackTile.asBlackTile(tile);

        BlackTile expectedBlackTile = new BlackTile(0, 0);

        Assertions.assertEquals(expectedBlackTile, actualBlackTile);

    }

    @Test
    void removePiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        Piece expectedPiece = new Piece(PieceColor.BLACK);

        blackTile.placePiece(expectedPiece);

        Assertions.assertEquals(expectedPiece, blackTile.removePiece());

    }

    public static void addNeighbors(Board board) {

        BlackTile.asBlackTile(board.getTiles()[0][0]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[0][2]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[0][4]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[0][6]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[1][1]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[1][3]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[1][5]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[1][7]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[2][0]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[2][2]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[2][4]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[2][6]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[3][1]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[3][3]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[3][5]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[3][7]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[4][0]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[4][2]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[4][4]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[4][6]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[5][1]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[5][3]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[5][5]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[5][7]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[6][0]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[6][2]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[6][4]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[6][6]).addNeighbors(board);

        BlackTile.asBlackTile(board.getTiles()[7][1]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[7][3]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[7][5]).addNeighbors(board);
        BlackTile.asBlackTile(board.getTiles()[7][7]).addNeighbors(board);
    }


}
