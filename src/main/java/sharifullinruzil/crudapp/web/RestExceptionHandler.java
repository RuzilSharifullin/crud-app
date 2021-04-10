package sharifullinruzil.crudapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sharifullinruzil.crudapp.util.exception.ErrorMessage;
import sharifullinruzil.crudapp.util.exception.ErrorType;
import sharifullinruzil.crudapp.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static sharifullinruzil.crudapp.util.ValidationUtil.getRootCause;
import static sharifullinruzil.crudapp.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorMessage(req, e, DATA_NOT_FOUND, false);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleError(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorMessage(req, e, DATA_ERROR, false);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetErrorMessage(req, e, VALIDATION_ERROR, false);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorMessage(req, e, APP_ERROR, true);
    }

    private ResponseEntity<ErrorMessage> logAndGetErrorMessage(HttpServletRequest req, Exception e, ErrorType errorType, boolean logStackTrace, String... details) {
        Throwable rootCause = getRootCause(e);
        if (logStackTrace) {
            log.error("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return ResponseEntity.status(errorType.getStatus()).body(new ErrorMessage(req.getRequestURL(),
                errorType, details.length != 0 ? details : new String[]{rootCause.getMessage()}));
    }
}
