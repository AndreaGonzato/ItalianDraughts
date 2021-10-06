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
        initPiecesDebug();
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

    // TODO remove this
    private void initPiecesDebug() {
        new BlackPiece(BlackTile.asBlackTile(tiles[0][0]));
        new WhitePiece(BlackTile.asBlackTile(tiles[3][3]));
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
        if (!(o instanceof Board board)) return false;

        return Arrays.deepEquals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
