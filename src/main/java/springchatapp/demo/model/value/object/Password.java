package springchatapp.demo.model.value.object;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Password {
  private final String password;

  public static Password noValidateOf(String password) {
    return Objects.isNull(password) ? new Password("") : new Password(password);
  }

  public String getValue() {
    return this.password;
  }

}
