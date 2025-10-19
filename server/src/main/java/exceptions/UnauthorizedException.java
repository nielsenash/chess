package exceptions;

import model.ErrorResponse;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return 401;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.getMessage());
    }
}
