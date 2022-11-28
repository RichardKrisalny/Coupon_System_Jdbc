package exeptions;

public class ConnectionPoolException extends RuntimeException{
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String get_connecting_filed) {
    }
}
