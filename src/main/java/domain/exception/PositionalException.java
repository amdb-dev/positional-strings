package domain.exception;

public class PositionalException extends RuntimeException{

    public PositionalException(String message) {
        super(message);
    }

    public PositionalException(Throwable t) {
        super(t);
    }

    public PositionalException(String message, Throwable t) {
        super(message, t);
    }
}
