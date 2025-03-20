package pet.project.postservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pet.project.postservice.exception.AuthHeaderIsMissing;
import pet.project.postservice.exception.BadRequestException;
import pet.project.postservice.exception.WrongTokenType;
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

    @ExceptionHandler(WrongTokenType.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleWrongTokenTypeException(WrongTokenType exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .type("WrongTokenTypeError")
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthHeaderIsMissing.class)
    public ResponseEntity<GeneralErrorDtoResponse> handleAuthHeaderIsMissingException(AuthHeaderIsMissing exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GeneralErrorDtoResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .type("AuthHeaderIsMissingError")
                        .message(exception.getMessage())
                        .build());
    }
}
