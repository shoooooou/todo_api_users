package springchatapp.demo.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import springchatapp.demo.model.resource.UserResource;

@Mapper
public interface UserMapper {
  Optional<UserResource> selectByUserName(final String uid);

//  TODO: ユーザー登録の時に実装する
//  int insertUser( addTaskResource);
}
