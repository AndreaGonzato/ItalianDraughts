package it.units.italiandraughts.logic;


import java.util.Arrays;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Board {
    private final Tile[][] tiles;
    public static final int SIZE = 8;

    public Board() {
        tiles = new Tile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Tile tile;
                if ((col + row) % 2 == 0) {
                    tile = new BlackTile(col, row);
                } else {
                    tile = new Tile(col, row);
                }
                tiles[row][col] = tile;
            }
        }
        matrixToStream(tiles).filter(tile -> tile instanceof BlackTile).map(BlackTile::asBlackTile)
                .forEach(blackTile -> blackTile.addNeighbors(this));
        initPieces();
    }

    public Board(Tile[][] tiles){
        this.tiles = tiles;
    }

    private void initPieces() {
        matrixToStream(tiles).filter(tile -> tile.getY() < 3 && (tile.getY() + tile.getX()) % 2 == 0)
                .map(BlackTile::asBlackTile)
                .forEach(BlackPiece::new);
        matrixToStream(tiles).filter(tile -> tile.getY() > 4 && (tile.getY() + tile.getX()) % 2 == 0)
                .map(BlackTile::asBlackTile)
                .forEach(WhitePiece::new);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
