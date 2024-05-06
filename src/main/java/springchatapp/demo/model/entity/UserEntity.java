package springchatapp.demo.model.entity;

import lombok.Builder;
import lombok.Getter;
import springchatapp.demo.model.value.object.Password;
import springchatapp.demo.model.value.object.Uid;

@Builder
@Getter
public class UserEntity {
  private Uid uid;
  private Password password;
}
