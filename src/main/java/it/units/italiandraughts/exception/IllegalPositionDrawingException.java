package it.units.italiandraughts.exception;

public class IllegalPositionDrawingException extends RuntimeException{

    public IllegalPositionDrawingException() {
        super("You can not draw in this position");
    }

    public IllegalPositionDrawingException(String message) {
        super(message);
    }
}
