package exceptions;

import model.ErrorResponse;

public class AlreadyTakenException extends Exception {

    public AlreadyTakenException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return 403;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.getMessage());
    }
}
