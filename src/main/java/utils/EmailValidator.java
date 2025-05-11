package utils;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static boolean validate(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }
}
