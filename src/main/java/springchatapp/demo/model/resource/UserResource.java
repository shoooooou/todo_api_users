package springchatapp.demo.model.resource;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResource {
  private String uid;
  private String password;
}
