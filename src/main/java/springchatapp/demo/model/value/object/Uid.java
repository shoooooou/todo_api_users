package springchatapp.demo.model.value.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@Data
public class Uid {
    private final String uid;

    public static Uid noValidateOf(String uid) {
        return Objects.isNull(uid) ? new Uid("") : new Uid(uid);
    }

    public String getValue() {
        return this.uid;
    }

}
