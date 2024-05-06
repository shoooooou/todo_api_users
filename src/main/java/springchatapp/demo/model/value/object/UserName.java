package springchatapp.demo.model.value.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@Data
public class UserName {
    private final String userName;

    public static UserName noValidateOf(String username) {
        return Objects.isNull(username) ? new UserName("") : new UserName(username);
    }

    public String getValue() {
        return this.userName;
    }

}
