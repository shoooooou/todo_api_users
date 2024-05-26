package springchatapp.demo.exceptions;

public class DatabaseAccessException extends Throwable {
  public DatabaseAccessException(String message) {
    super(message);
  }

  public DatabaseAccessException(String message, Throwable cause) {
    super(message, cause);
  }
}
