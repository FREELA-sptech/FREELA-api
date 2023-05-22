package freela.api.FREELAAPI.domain.exceptions;

import org.springframework.http.HttpStatus;

public class CreateOrderException extends RuntimeException {
    private final HttpStatus statusCode;
    public CreateOrderException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
