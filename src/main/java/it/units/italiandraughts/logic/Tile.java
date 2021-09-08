package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.Square;


import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Tile {

    protected final int x;
    protected final int y;
    private Square square;

    private static final Predicate<Integer> isValidCoordinatePredicate =
            coordinate -> (coordinate >= 0 && coordinate < Board.SIZE);

    protected static final BiPredicate<Integer, Integer> areValidCoordinatesBiPredicate =
            (coordinateX, coordinateY) -> isValidCoordinatePredicate.test(coordinateX) &&
                    isValidCoordinatePredicate.test(coordinateY);
    

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;

        if (x != tile.x) return false;
        if (y != tile.y) return false;
        return Objects.equals(square, tile.square);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (square != null ? square.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
