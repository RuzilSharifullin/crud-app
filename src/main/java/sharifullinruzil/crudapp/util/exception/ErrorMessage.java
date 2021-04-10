package sharifullinruzil.crudapp.util.exception;

public class ErrorMessage {
    private final CharSequence url;
    private final ErrorType type;
    private final String[] details;

    public ErrorMessage(CharSequence url, ErrorType type, String[] details) {
        this.url = url;
        this.type = type;
        this.details = details;
    }
}
