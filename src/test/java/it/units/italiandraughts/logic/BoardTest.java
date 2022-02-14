package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.Objects;

@ExtendWith(ApplicationExtension.class)
public class BoardTest {
    // TEST associated class : completed

    @Test
    void checkTilesInitialization() {
        Board board = Board.reset();
        board.initPieces();

        Tile[][] expectedTiles = setUpPieces();

        Assertions.assertTrue(Objects.deepEquals(board.getTiles(), expectedTiles));
    }

    @Test
    void checkRemovePieces() {
        Board board = Board.reset();

        Tile[][] actualTiles = board.getTiles();
        Tile[][] expectedTiles = initTiles();

        Assertions.assertTrue(Objects.deepEquals(expectedTiles, actualTiles));
    }

    private Tile[][] setUpPieces() {
        Tile[][] tiles = initTiles();

        placePieceOnTile(BlackTile.asBlackTile(tiles[0][0]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[0][2]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[0][4]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[0][6]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[1][1]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[1][3]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[1][5]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[1][7]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[2][0]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[2][2]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[2][4]), PieceColor.BLACK);
        placePieceOnTile(BlackTile.asBlackTile(tiles[2][6]), PieceColor.BLACK);

        placePieceOnTile(BlackTile.asBlackTile(tiles[5][1]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[5][3]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[5][5]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[5][7]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[6][0]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[6][2]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[6][4]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[6][6]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[7][1]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[7][3]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[7][5]), PieceColor.WHITE);
        placePieceOnTile(BlackTile.asBlackTile(tiles[7][7]), PieceColor.WHITE);

        return tiles;
    }

    public static Tile[][] initTiles(){
        Tile[][] tiles = new Tile[Board.SIZE][Board.SIZE];

        // create all the tiles
        initTile(tiles, 0, 0, true);
        initTile(tiles, 1, 0, false);
        initTile(tiles, 2, 0, true);
        initTile(tiles, 3, 0, false);
        initTile(tiles, 4, 0, true);
        initTile(tiles, 5, 0, false);
        initTile(tiles, 6, 0, true);
        initTile(tiles, 7, 0, false);

        initTile(tiles, 0, 1, false);
        initTile(tiles, 1, 1, true);
        initTile(tiles, 2, 1, false);
        initTile(tiles, 3, 1, true);
        initTile(tiles, 4, 1, false);
        initTile(tiles, 5, 1, true);
        initTile(tiles, 6, 1, false);
        initTile(tiles, 7, 1, true);

        initTile(tiles, 0, 2, true);
        initTile(tiles, 1, 2, false);
        initTile(tiles, 2, 2, true);
        initTile(tiles, 3, 2, false);
        initTile(tiles, 4, 2, true);
        initTile(tiles, 5, 2, false);
        initTile(tiles, 6, 2, true);
        initTile(tiles, 7, 2, false);

        initTile(tiles, 0, 3, false);
        initTile(tiles, 1, 3, true);
        initTile(tiles, 2, 3, false);
        initTile(tiles, 3, 3, true);
        initTile(tiles, 4, 3, false);
        initTile(tiles, 5, 3, true);
        initTile(tiles, 6, 3, false);
        initTile(tiles, 7, 3, true);

        initTile(tiles, 0, 4, true);
        initTile(tiles, 1, 4, false);
        initTile(tiles, 2, 4, true);
        initTile(tiles, 3, 4, false);
        initTile(tiles, 4, 4, true);
        initTile(tiles, 5, 4, false);
        initTile(tiles, 6, 4, true);
        initTile(tiles, 7, 4, false);

        initTile(tiles, 0, 5, false);
        initTile(tiles, 1, 5, true);
        initTile(tiles, 2, 5, false);
        initTile(tiles, 3, 5, true);
        initTile(tiles, 4, 5, false);
        initTile(tiles, 5, 5, true);
        initTile(tiles, 6, 5, false);
        initTile(tiles, 7, 5, true);

        initTile(tiles, 0, 6, true);
        initTile(tiles, 1, 6, false);
        initTile(tiles, 2, 6, true);
        initTile(tiles, 3, 6, false);
        initTile(tiles, 4, 6, true);
        initTile(tiles, 5, 6, false);
        initTile(tiles, 6, 6, true);
        initTile(tiles, 7, 6, false);

        initTile(tiles, 0, 7, false);
        initTile(tiles, 1, 7, true);
        initTile(tiles, 2, 7, false);
        initTile(tiles, 3, 7, true);
        initTile(tiles, 4, 7, false);
        initTile(tiles, 5, 7, true);
        initTile(tiles, 6, 7, false);
        initTile(tiles, 7, 7, true);

        return tiles;
    }

    private static void initTile(Tile[][] tiles, int x, int y, boolean blackTile){
        if (blackTile) {
            tiles[y][x] = new BlackTile(x, y);
        } else {
            tiles[y][x] = new Tile(x, y);
        }
    }

    public static void placePieceOnTile(BlackTile blackTile, PieceColor pieceColor){
        Piece piece = switch (pieceColor) {
            case BLACK -> new BlackPiece();
            case WHITE -> new WhitePiece();
        };
        blackTile.placePiece(piece);
    }


}
