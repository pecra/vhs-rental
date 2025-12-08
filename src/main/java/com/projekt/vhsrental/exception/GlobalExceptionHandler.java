package com.projekt.vhsrental.exception;


import com.projekt.vhsrental.model.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException exception){

        log.warn("NotFoundException: {}", exception.getMessage(), exception);

        String mess = messageSource.getMessage(exception.getMessage(), null,exception.getMessage(), null);

        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);

    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<ErrorDTO> handleForbiddenActionException(ForbiddenActionException exception){

        log.warn("ForbiddenActionException: {}", exception.getMessage(), exception);

        String mess = messageSource.getMessage(exception.getMessage(), null,exception.getMessage(), null);

        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);

    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyExistsException(AlreadyExistsException exception){

        log.warn("AlreadyExistsException: {}", exception.getMessage(), exception);

        String mess = messageSource.getMessage(exception.getMessage(), null,exception.getMessage(), null);

        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException exception){

        log.warn("ValidationException: {}", exception.getMessage(), exception);

        var fierr = exception.getBindingResult().getFieldError();
        String err = fierr != null ? fierr.getDefaultMessage() : "validation.error";

        String mess = messageSource.getMessage(err, null, err, null);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        log.warn("DataIntegrityViolation: {}", exception.getMessage(), exception);

        String mess = messageSource.getMessage("user.already.exists", null,exception.getMessage(), null);

        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception){

        log.error("Exception: {}", exception.getMessage(), exception);

        String mess = messageSource.getMessage("unexpected.error", null,exception.getMessage(), null);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorDTO(status, mess), status);

    }
}
