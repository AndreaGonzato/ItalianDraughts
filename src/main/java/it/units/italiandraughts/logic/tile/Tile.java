package it.units.italiandraughts.logic.tile;

import it.units.italiandraughts.logic.Board;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Tile {

    private static final Predicate<Integer> isValidCoordinatePredicate =
            coordinate -> (coordinate >= 0 && coordinate < Board.SIZE);
    public static final BiPredicate<Integer, Integer> areValidCoordinatesBiPredicate =
            (coordinateX, coordinateY) -> isValidCoordinatePredicate.test(coordinateX) &&
                    isValidCoordinatePredicate.test(coordinateY);
    protected final int x;
    protected final int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Tile generateTile(int x, int y) {
        if ((x + y) % 2 == 0) {
            return new BlackTile(x, y);
        } else {
            return new Tile(x, y);
        }
    }

    public int calculateDistance(Tile otherTile) {
        int deltaX = this.x - otherTile.x;
        int deltaY = this.y - otherTile.y;
        return Math.abs(Math.max(deltaX, deltaY));
    }

    public String getDirection(Tile otherTile) {

        if (this.equals(otherTile)) {
            throw new IllegalArgumentException("otherTile must be a different tile");
        }

        if (this.y - otherTile.y > 0) {
            return "top";
        } else {
            return "bottom";
        }

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
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
