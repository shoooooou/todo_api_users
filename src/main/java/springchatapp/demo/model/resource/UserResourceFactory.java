package springchatapp.demo.model.resource;

import springchatapp.demo.model.entity.UserEntity;

public class UserResourceFactory {

  public static UserResource create(UserEntity userEntity) {
    return UserResource.builder()
        .uid(userEntity.getUid().getValue())
        .password(userEntity.getPassword().getValue())
        .build();
  }
}
