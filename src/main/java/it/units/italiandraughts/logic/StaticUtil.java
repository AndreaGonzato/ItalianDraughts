package it.units.italiandraughts.logic;

import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StaticUtil {
    public static <T> Stream<T> matrixToStream(T[][] matrix) {
        return Arrays.stream(matrix).flatMap(Arrays::stream);
    }

}
