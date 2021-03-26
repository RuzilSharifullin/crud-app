package sharifullinruzil.crudapp.util;

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
}
