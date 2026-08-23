package util;

public class ValidationUtils {

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    public static boolean isLengthBetween(String value, int min, int max) {
        String trimmed = trim(value);
        return trimmed.length() >= min && trimmed.length() <= max;
    }

    public static boolean isMaxLength(String value, int max) {
        return trim(value).length() <= max;
    }

    public static boolean isEmail(String value) {
        if (isBlank(value)) {
            return true;
        }
        return trim(value).matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
