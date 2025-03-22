package pet.project.postservice.exception;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
