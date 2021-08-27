package it.units.italiandraughts.exception;

public class IllegalButtonClickException extends RuntimeException {

    public IllegalButtonClickException() {
        super("An illegal click was performed on a button");
    }

    public IllegalButtonClickException(String message) {
        super(message);
    }

}