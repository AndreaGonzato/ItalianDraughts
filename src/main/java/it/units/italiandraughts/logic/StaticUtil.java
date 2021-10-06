package it.units.italiandraughts.logic;

import javafx.stage.Screen;

import java.util.Arrays;
import java.util.stream.Stream;

public class StaticUtil {

    public static <T> Stream<T> matrixToStream(T[][] matrix) {
        return Arrays.stream(matrix).flatMap(Arrays::stream);
    }

    public static double getScreenWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getScreenHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }

}
