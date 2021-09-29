package learnjwt.coursejwt.exception.errors;

import java.io.Serializable;

public class InvalidJwtAuthenticationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1l;

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
