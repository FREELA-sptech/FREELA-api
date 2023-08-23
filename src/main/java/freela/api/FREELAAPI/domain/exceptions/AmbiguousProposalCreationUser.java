package freela.api.FREELAAPI.domain.exceptions;

import org.springframework.http.HttpStatus;

public class AmbiguousProposalCreationUser extends RuntimeException{
    private final HttpStatus statusCode;
    public AmbiguousProposalCreationUser(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
