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

    static Collector<GraphPath<BlackTile, Edge>, List<GraphPath<BlackTile, Edge>>, List<GraphPath<BlackTile, Edge>>> getLongestPaths() {
        Comparator<GraphPath<BlackTile, Edge>> comparator = Comparator.comparingDouble(GraphPath::getWeight);
        return Collector.of(
                ArrayList::new,
                (list, path) -> {
                    int result;
                    if (list.isEmpty() || (result = comparator.compare(path, list.get(0))) == 0) {
                        list.add(path);
                    } else if (result > 0) {
                        list.clear();
                        list.add(path);
                    }
                },
                (list1, list2) -> {
                    if (list1.isEmpty()) {
                        return list2;
                    }
                    if (list2.isEmpty()) {
                        return list1;
                    }
                    int result = comparator.compare(list1.get(0), list2.get(0));
                    if (result < 0) {
                        return list2;
                    } else if (result > 0) {
                        return list1;
                    } else {
                        list1.addAll(list2);
                        return list1;
                    }
                }
        );
    }
}
