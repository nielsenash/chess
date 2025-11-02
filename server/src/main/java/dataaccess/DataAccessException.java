package dataaccess;

import model.ErrorResponse;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }

    public int getStatusCode() {
        return 500;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.getMessage());
    }
}
