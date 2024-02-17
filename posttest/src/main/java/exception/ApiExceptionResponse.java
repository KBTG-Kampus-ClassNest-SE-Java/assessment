package exception;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public class ApiExceptionResponse {

  private final String message;

  private final HttpStatus code;

  private final ZonedDateTime dateTime;

  public ApiExceptionResponse(String message, HttpStatus code, ZonedDateTime dateTime) {
    this.message = message;
    this.code = code;
    this.dateTime = dateTime;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getCode() {
    return code;
  }

  public ZonedDateTime getDateTime() {
    return dateTime;
  }
}
