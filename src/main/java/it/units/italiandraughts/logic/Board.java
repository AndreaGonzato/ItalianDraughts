package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceColor;

import java.util.Arrays;

public class Board {
    private final Tile[][] tiles;
    public static final int SIZE = 8;

    public Board() {
        tiles = new Tile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Tile tile = new Tile(col, row);
                tiles[row][col] = tile;
            }
        }
        initPieces();
    }

    public void initPieces(){
        Arrays.stream(tiles).flatMap(Arrays::stream).filter(t -> t.getY() < 3 && (t.getY() + t.getX()) % 2 == 0)
                .forEach(t -> t.placePiece(new Piece(PieceColor.PLAYER2)));
        Arrays.stream(tiles).flatMap(Arrays::stream).filter(t -> t.getY() > 4 && (t.getY() + t.getX()) % 2 == 0)
                .forEach(t -> t.placePiece(new Piece(PieceColor.PLAYER1)));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Board{ board=\n");
        Arrays.stream(tiles).forEachOrdered(result::append);
        result.append(" }");
        return result.toString();
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
