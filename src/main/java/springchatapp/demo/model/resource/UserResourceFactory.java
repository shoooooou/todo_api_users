package springchatapp.demo.model.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import springchatapp.demo.model.entity.UserEntity;

public class UserResourceFactory {
  @Autowired
  private static PasswordEncoder passwordEncoder;

  public static UserResource create(UserEntity userEntity) {
    return UserResource.builder()
        .uid(userEntity.getUid().getValue())
        .password(passwordEncoder.encode(userEntity.getPassword().getValue()))
        .build();
  }
}
