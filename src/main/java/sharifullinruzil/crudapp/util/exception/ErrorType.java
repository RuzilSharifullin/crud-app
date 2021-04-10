package sharifullinruzil.crudapp.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND),
    DATA_ERROR(HttpStatus.CONFLICT),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY);

    private final HttpStatus status;


    ErrorType(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
