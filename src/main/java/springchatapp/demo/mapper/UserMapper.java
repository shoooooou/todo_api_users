package springchatapp.demo.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import springchatapp.demo.model.resource.UserResource;

@Mapper
public interface UserMapper {
  Optional<UserResource> selectByUserName(final String uid);

  int insertUser(UserResource userResource);

}
