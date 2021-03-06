package it.units.italiandraughts.logic;


import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Board {

    public static final int SIZE = 8;
    private static Board instance;
    private final Tile[][] tiles;

    private Board() {
        tiles = new Tile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                tiles[row][col] = Tile.generateTile(col, row);
            }
        }
    }

    public static Board getBoard() {
        if (instance == null) {
            reset();
        }
        return instance;
    }

    public static Board reset() {
        instance = new Board();
        instance.assignNeighborsOfBlackTiles();
        return instance;
    }

    private void assignNeighborsOfBlackTiles() {
        matrixToStream(tiles).filter(tile -> tile instanceof BlackTile).map(BlackTile::asBlackTile)
                .forEach(BlackTile::populateNeighborsFromBoard);
    }

    public void initPieces() {
        Supplier<Stream<BlackTile>> blackTilesSupplier = () -> getEmptyBlackTiles().stream();
        blackTilesSupplier.get().filter(tile -> tile.getY() < 3).forEach(tile -> tile.placePiece(new BlackPiece()));
        blackTilesSupplier.get().filter(tile -> tile.getY() > 4).forEach(tile -> tile.placePiece(new WhitePiece()));
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

    public List<BlackTile> getEmptyBlackTiles() {
        return matrixToStream(tiles)
                .filter(Tile::isEmpty)
                .filter(tile -> tile instanceof BlackTile)
                .map(BlackTile::asBlackTile)
                .collect(Collectors.toList());
    }

    public List<BlackTile> getFullBlackTiles() {
        return matrixToStream(tiles)
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .collect(Collectors.toList());
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
