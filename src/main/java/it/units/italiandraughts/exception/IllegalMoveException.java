package it.units.italiandraughts.exception;

public class IllegalMoveException extends ItalianDraughtsException{
    public IllegalMoveException() {
        super("The required move is illegal");
    }

    public IllegalMoveException(String message) {
        super(message);
    }

}
