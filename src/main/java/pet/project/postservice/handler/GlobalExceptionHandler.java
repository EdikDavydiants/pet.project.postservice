package pet.project.postservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pet.project.postservice.exception.AuthHeaderIsMissingException;
import pet.project.postservice.exception.BadRequestException;
import pet.project.postservice.exception.NotFoundException;
import pet.project.postservice.exception.PostNotFoundException;
import pet.project.postservice.exception.WrongTokenTypeException;
import pet.project.postservice.model.dto.GeneralErrorDtoResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GeneralErrorDtoResponse> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .type("Unknown error")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .type("BadRequestError")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(WrongTokenTypeException.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleWrongTokenTypeException(WrongTokenTypeException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .type("WrongTokenTypeError")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthHeaderIsMissingException.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleAuthHeaderIsMissingException(AuthHeaderIsMissingException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .type("AuthHeaderIsMissingError")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<GeneralErrorDtoResponse> handlePostNotFoundException(PostNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .type("PostNotFoundError")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .type("NotFoundError")
                        .message(exception.getMessage())
                        .build());
    }
}
