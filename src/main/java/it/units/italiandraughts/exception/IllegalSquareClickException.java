package it.units.italiandraughts.exception;

public class IllegalSquareClickException extends RuntimeException {

    public IllegalSquareClickException() {
        super("An illegal click was performed on a square");
    }

    public IllegalSquareClickException(String message) {
        super(message);
    }

}
