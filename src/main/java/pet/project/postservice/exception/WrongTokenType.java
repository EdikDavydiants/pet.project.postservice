package pet.project.postservice.exception;

public class WrongTokenType extends RuntimeException {

    public WrongTokenType(String message) {
        super(message);
    }
}
