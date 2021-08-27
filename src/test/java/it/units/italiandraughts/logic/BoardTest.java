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
        Tile[][] expectedTiles = new Tile[8][8];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                expectedTiles[i][j] = new Tile(j, i);
            }
        }

        Board board = new Board();
        board.emptyPiecesFromTiles();

        Assertions.assertArrayEquals(expectedTiles, board.getTiles());
    }


    @Test
    void moveTopRightPiecePlayer1ToLeft() {
        Tile[][] expectedTiles = setUpBoard();
        expectedTiles[4][6].placePiece(new Piece(PieceType.PLAYER1));
        expectedTiles[5][7].removePiece();

        Board board = new Board();
        Game game = new Game(board, new Player("1", PieceType.PLAYER1),
                new Player("2", PieceType.PLAYER2));
        game.move(7, 5, 6, 4, false);

        Assertions.assertArrayEquals(expectedTiles, board.getTiles());
    }



    private Tile[][] setUpBoard() {
        Tile[][] tiles = new Tile[Board.SIZE][Board.SIZE];

        // create all the tiles
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(j, i);
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


        tiles[5][1].placePiece(new Piece(PieceType.PLAYER1));
        tiles[5][3].placePiece(new Piece(PieceType.PLAYER1));
        tiles[5][5].placePiece(new Piece(PieceType.PLAYER1));
        tiles[5][7].placePiece(new Piece(PieceType.PLAYER1));
        tiles[6][0].placePiece(new Piece(PieceType.PLAYER1));
        tiles[6][2].placePiece(new Piece(PieceType.PLAYER1));
        tiles[6][4].placePiece(new Piece(PieceType.PLAYER1));
        tiles[6][6].placePiece(new Piece(PieceType.PLAYER1));
        tiles[7][1].placePiece(new Piece(PieceType.PLAYER1));
        tiles[7][3].placePiece(new Piece(PieceType.PLAYER1));
        tiles[7][5].placePiece(new Piece(PieceType.PLAYER1));
        tiles[7][7].placePiece(new Piece(PieceType.PLAYER1));

        return tiles;
    }


}
