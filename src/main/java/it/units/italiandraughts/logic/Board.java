package it.units.italiandraughts.logic;


import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Board {

    private Tile[][] tiles;
    public static final int SIZE = 8;
    private static Board instance;

    public static Board getBoard() {
        return getBoard(false);
    }

    public static Board getBoard(boolean reset) {
        if (instance == null || reset) {
            instance = new Board();
        }
        return instance;
    }

    private Board() {
        tiles = new Tile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                tiles[row][col] = Tile.generateTile(col, row);
            }
        }


        initPieces();
        //initPiecesDebug(); // TODO remove this line
    }

    public void assignNeighborsOfBlackTiles(){
        matrixToStream(tiles).filter(tile -> tile instanceof BlackTile).map(BlackTile::asBlackTile)
                .forEach(BlackTile::addNeighbors);
    }

    private void initPieces() {
        Supplier<Stream<BlackTile>> blackTilesSupplier = () -> matrixToStream(tiles)
                .filter(tile -> tile instanceof BlackTile)
                .map(BlackTile::asBlackTile);
        blackTilesSupplier.get().filter(tile -> tile.getY() < 3).forEach(BlackPiece::new);
        blackTilesSupplier.get().filter(tile -> tile.getY() > 4).forEach(WhitePiece::new);
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

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
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
