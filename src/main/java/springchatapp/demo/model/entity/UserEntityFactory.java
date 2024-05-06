package springchatapp.demo.model.entity;

import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.model.value.object.Password;
import springchatapp.demo.model.value.object.Uid;

public class UserEntityFactory {
  public static UserEntity create(UserResource userResource) {
    return UserEntity.builder()
        .uid(Uid.noValidateOf(userResource.getUid()))
        .password(Password.noValidateOf(userResource.getPassword()))
        .build();
  }
}
