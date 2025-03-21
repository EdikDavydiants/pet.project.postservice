package pet.project.postservice.exception;

public class WrongTokenTypeException extends RuntimeException {

    public WrongTokenTypeException(String message) {
        super(message);
    }
}
