package it.units.italiandraughts.exception;

public class IllegalDrawingPositionException extends ItalianDraughtsException {

    public IllegalDrawingPositionException() {
        super("Cannot draw in this position.");
    }

    public IllegalDrawingPositionException(String message) {
        super(message);
    }

}
