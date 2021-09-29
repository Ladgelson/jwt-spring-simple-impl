package learnjwt.coursejwt.exception.handler;

import learnjwt.coursejwt.exception.errors.InvalidJwtAuthenticationException;
import learnjwt.coursejwt.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<ExceptionResponse> invalidJwtAuthentication(Exception ex, WebRequest req) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .date(new Date())
                        .message(ex.getMessage())
                        .description(req.getDescription(false))
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
