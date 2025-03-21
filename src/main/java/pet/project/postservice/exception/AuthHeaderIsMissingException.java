package pet.project.postservice.exception;

public class AuthHeaderIsMissingException extends RuntimeException {

    public AuthHeaderIsMissingException(String message) {
        super(message);
    }
}
