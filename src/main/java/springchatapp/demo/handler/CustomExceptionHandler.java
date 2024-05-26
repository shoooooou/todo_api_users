package springchatapp.demo.handler;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springchatapp.demo.exceptions.DatabaseAccessException;
import springchatapp.demo.exceptions.EncryptionException;

@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(EncryptionException.class)
  public ResponseEntity<?> handleEncryptionException(EncryptionException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(400, "暗号または複合化に失敗しました" + ex.getMessage()));
  }

  @ExceptionHandler(DatabaseAccessException.class)
  public ResponseEntity<?> handleDatabaseAccessException(DatabaseAccessException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(400, "データベース関連エラー:" + ex.getMessage()));
  }

  public class ErrorResponse {
    @Getter
    private String message;
    @Getter
    private int status;

    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }
  }
}

