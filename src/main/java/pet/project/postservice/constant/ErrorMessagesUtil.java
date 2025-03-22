package pet.project.postservice.constant;

public class ErrorMessagesUtil {

    public static String combine(String entityName, String message) {
        return entityName + " " + message;
    }

    public static final String WRONG_TOKEN_TYPE = "Type of the token is not Bearer!";

    public static final String AUTH_HEADER_IS_MISSING = "Authorization header is missing!";

    public static final String FORBIDDEN_REQUEST = "Forbidden request!";

    public static final String NOT_FOUND = "hasn't been found!";

    public static final String SUCCESSFUL_DELETING = "has been successfully deleted.";
}
