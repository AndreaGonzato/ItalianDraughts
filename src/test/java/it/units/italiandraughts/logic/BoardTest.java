package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BoardTest {
    @Test
    void initialization() {
        Tile[][] expectedTiles = setUpBoard();

        Board board = new Board();

        Assertions.assertArrayEquals(expectedTiles, board.getTiles());
    }

    @Test
    void empty() {
        Piece[][] expectedPieces = new Piece[8][8];

        Board board = new Board();
        board.empty();

        Assertions.assertArrayEquals(expectedPieces, board.getTiles());
    }

    // TODO this shall be uncommented when move() works again

    /*
    @Test
    void moveTopRightPiecePlayer1ToLeft() {
        Piece[][] expectedBoard = setUpBoard();
        expectedBoard[4][6] = expectedBoard[5][7];
        expectedBoard[5][7] = null;

        Board board = new Board();
        board.move(board.getBoard()[5][7], 6, 4);

        Assertions.assertArrayEquals(expectedBoard, board.getBoard());
    }

     */

    private Tile[][] setUpBoard() {
        Tile[][] tiles = new Tile[8][8];

        // create all the tiles
        for (int i = 0 ; i<8 ; i++){
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        tiles[0][0].placePiece(new Piece(PieceType.PLAYER2));
        tiles[0][2].placePiece(new Piece(PieceType.PLAYER2));
        tiles[0][4].placePiece(new Piece(PieceType.PLAYER2));
        tiles[0][6].placePiece(new Piece(PieceType.PLAYER2));
        tiles[1][1].placePiece(new Piece(PieceType.PLAYER2));
        tiles[1][3].placePiece(new Piece(PieceType.PLAYER2));
        tiles[1][5].placePiece(new Piece(PieceType.PLAYER2));
        tiles[1][7].placePiece(new Piece(PieceType.PLAYER2));
        tiles[2][0].placePiece(new Piece(PieceType.PLAYER2));
        tiles[2][2].placePiece(new Piece(PieceType.PLAYER2));
        tiles[2][4].placePiece(new Piece(PieceType.PLAYER2));
        tiles[2][6].placePiece(new Piece(PieceType.PLAYER2));


        tiles[5][1].placePiece(new Piece(PieceType.PLAYER2));
        tiles[5][3].placePiece(new Piece(PieceType.PLAYER2));
        tiles[5][5].placePiece(new Piece(PieceType.PLAYER2));
        tiles[5][7].placePiece(new Piece(PieceType.PLAYER2));
        tiles[6][0].placePiece(new Piece(PieceType.PLAYER2));
        tiles[6][2].placePiece(new Piece(PieceType.PLAYER2));
        tiles[6][4].placePiece(new Piece(PieceType.PLAYER2));
        tiles[6][6].placePiece(new Piece(PieceType.PLAYER2));
        tiles[7][1].placePiece(new Piece(PieceType.PLAYER2));
        tiles[7][3].placePiece(new Piece(PieceType.PLAYER2));
        tiles[7][5].placePiece(new Piece(PieceType.PLAYER2));
        tiles[7][7].placePiece(new Piece(PieceType.PLAYER2));

        return tiles;
    }


}
