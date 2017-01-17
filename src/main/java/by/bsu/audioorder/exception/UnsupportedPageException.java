package by.bsu.audioorder.exception;

public class UnsupportedPageException extends Exception {
    public UnsupportedPageException() {
    }

    public UnsupportedPageException(String message) {
        super(message);
    }

    public UnsupportedPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPageException(Throwable cause) {
        super(cause);
    }
}
