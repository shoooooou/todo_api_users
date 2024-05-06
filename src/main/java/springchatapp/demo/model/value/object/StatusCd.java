package springchatapp.demo.model.value.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@Data
public class StatusCd {
    private final String statusCd;

    public static StatusCd noValidateOf(String statusCd) {
        return Objects.isNull(statusCd) ? new StatusCd("") : new StatusCd(statusCd);
    }

    public String getValue() {
        return this.statusCd;
    }
}
