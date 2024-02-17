package exception;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFound(NotFoundException notFoundException) {

    ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
        notFoundException.getMessage(),
        HttpStatus.NOT_FOUND,
        ZonedDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionResponse,HttpStatus.NOT_FOUND);
  }


}
