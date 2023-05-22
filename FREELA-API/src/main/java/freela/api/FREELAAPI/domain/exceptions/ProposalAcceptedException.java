package freela.api.FREELAAPI.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ProposalAcceptedException extends RuntimeException {
    private final HttpStatus statusCode;
    public ProposalAcceptedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
