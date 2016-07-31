package by.epam.audioorder.exception;

public class UnsupportedCommandException extends Exception {
    public UnsupportedCommandException() {
    }

    public UnsupportedCommandException(String message) {
        super(message);
    }

    public UnsupportedCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedCommandException(Throwable cause) {
        super(cause);
    }
}
