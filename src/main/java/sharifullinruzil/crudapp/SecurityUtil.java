package sharifullinruzil.crudapp;

public class SecurityUtil {
    public static final int USER_ID = 111;
    public static final int USER_TARGET_CALORIES = 2000;

    public static int getAuthenticatedUserId() {
        return USER_ID;
    }

    public static int getAuthenticatedUserTargetCalories() {
        return USER_TARGET_CALORIES;
    }
}
