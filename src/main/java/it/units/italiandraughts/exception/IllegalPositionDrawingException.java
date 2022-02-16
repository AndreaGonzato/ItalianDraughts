package it.units.italiandraughts.exception;

public class IllegalPositionDrawingException extends ItalianDraughtsException{

    public IllegalPositionDrawingException() {
        super("Can not draw in this position");
    }

    public IllegalPositionDrawingException(String message) {
        super(message);
    }
}
