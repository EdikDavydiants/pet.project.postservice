package pet.project.postservice.exception;

public class AuthHeaderIsMissing extends RuntimeException {

    public AuthHeaderIsMissing(String message) {
        super(message);
    }
}
