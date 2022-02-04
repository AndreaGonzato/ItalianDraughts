package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;

import java.util.Objects;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Player {

    private final String name;
    private final PieceColor pieceColor;

    public Player(String name, PieceColor pieceColor){
        this.pieceColor = pieceColor;
        this.name = name;
    }

    void updateMovablePieces() {
        matrixToStream(Board.getBoard().getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(pieceColor))
                .forEach(tile -> tile.getPiece().updateMovable());
    }

    int countMovablePieces(){
        return (int) matrixToStream(Board.getBoard().getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(pieceColor)
                        && tile.getPiece().isMovable())
                .count();
    }

    public String getName() {
        return name;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;

        if (!Objects.equals(name, player.name)) return false;
        return pieceColor == player.pieceColor;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pieceColor.hashCode();
        return result;
    }
}
