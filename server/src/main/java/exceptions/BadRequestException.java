package exceptions;

import model.ErrorResponse;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return 400;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.getMessage());
    }
}
