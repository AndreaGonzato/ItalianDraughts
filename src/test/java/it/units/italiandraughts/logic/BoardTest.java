package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void placePieceInInitialPosition() {
        Tile[][] expectedTiles = setUpBoard();

        Board board = new Board();

        Assertions.assertArrayEquals(expectedTiles, board.getTiles());
    }

    @Test
    void moveTopRightPiecePlayer1ToLeft() {
        Tile[][] expectedTiles = setUpBoard();
        placePieceOnTile(expectedTiles[4][6], PieceColor.WHITE);
        expectedTiles[5][7].removePiece();

        Board board = new Board();
        Game game = new Game(board, new Player("1", PieceColor.WHITE),
                new Player("2", PieceColor.BLACK));
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


        placePieceOnTile(tiles[0][0], PieceColor.BLACK);
        placePieceOnTile(tiles[0][2], PieceColor.BLACK);
        placePieceOnTile(tiles[0][4], PieceColor.BLACK);
        placePieceOnTile(tiles[0][6], PieceColor.BLACK);
        placePieceOnTile(tiles[1][1], PieceColor.BLACK);
        placePieceOnTile(tiles[1][3], PieceColor.BLACK);
        placePieceOnTile(tiles[1][5], PieceColor.BLACK);
        placePieceOnTile(tiles[1][7], PieceColor.BLACK);
        placePieceOnTile(tiles[2][0], PieceColor.BLACK);
        placePieceOnTile(tiles[2][2], PieceColor.BLACK);
        placePieceOnTile(tiles[2][4], PieceColor.BLACK);
        placePieceOnTile(tiles[2][6], PieceColor.BLACK);

        placePieceOnTile(tiles[5][1], PieceColor.WHITE);
        placePieceOnTile(tiles[5][3], PieceColor.WHITE);
        placePieceOnTile(tiles[5][5], PieceColor.WHITE);
        placePieceOnTile(tiles[5][7], PieceColor.WHITE);
        placePieceOnTile(tiles[6][0], PieceColor.WHITE);
        placePieceOnTile(tiles[6][2], PieceColor.WHITE);
        placePieceOnTile(tiles[6][4], PieceColor.WHITE);
        placePieceOnTile(tiles[6][6], PieceColor.WHITE);
        placePieceOnTile(tiles[7][1], PieceColor.WHITE);
        placePieceOnTile(tiles[7][3], PieceColor.WHITE);
        placePieceOnTile(tiles[7][5], PieceColor.WHITE);
        placePieceOnTile(tiles[7][7], PieceColor.WHITE);

        return tiles;
    }

    private void placePieceOnTile(Tile tile, PieceColor pieceColor){
        tile.placePiece(new Piece(pieceColor, tile));
    }

}
