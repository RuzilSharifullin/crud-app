package sharifullinruzil.crudapp.util;

import org.springframework.core.NestedExceptionUtils;
import sharifullinruzil.crudapp.util.exception.NotFoundException;

public class ValidationUtil {

    public static <T> T checkIfNotFound(T object, int id) {
        checkIfNotFound(object != null, id);
        return object;
    }

    public static void checkIfNotFound(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Unable to find entity with " + msg);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
