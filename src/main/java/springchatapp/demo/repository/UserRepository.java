package springchatapp.demo.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import springchatapp.demo.mapper.UserMapper;
import springchatapp.demo.model.resource.UserResource;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {
  final private UserMapper userMapper;

  public Optional<UserResource> getUser(final String userName) {
    try {
      Optional<UserResource> optionalUserResource = userMapper.selectByUserName(userName);
      return optionalUserResource;
    } catch (Exception ex) {
      log.error("Database access error target user id: {}", userName);
      throw ex;
    }
  }

  public int addUser(UserResource userResource) {
    try {
      int result = userMapper.insertUser(userResource);
      return result;
    } catch (Exception ex) {
      log.error("Database access error target user id: {}", userResource.getUid());
      throw ex;
    }
  }

}
