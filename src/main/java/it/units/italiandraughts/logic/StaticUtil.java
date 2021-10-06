package it.units.italiandraughts.logic;

import java.util.Arrays;
import java.util.stream.Stream;

public class StaticUtil {
    public static <T> Stream<T> matrixToStream(T[][] matrix) {
        return Arrays.stream(matrix).flatMap(Arrays::stream);
    }

}
