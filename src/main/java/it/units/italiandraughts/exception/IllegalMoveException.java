package it.units.italiandraughts.exception;

public class IllegalMoveException extends ItalianDraughtsException {

    public IllegalMoveException() {
        super("The requested move is illegal.");
    }

    public IllegalMoveException(String message) {
        super(message);
    }

}
