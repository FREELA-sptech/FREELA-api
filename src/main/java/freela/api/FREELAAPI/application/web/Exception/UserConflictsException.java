package freela.api.FREELAAPI.application.web.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserConflictsException extends RuntimeException {
    public UserConflictsException(String message) {
        super(message);
    }
}
