package springchatapp.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springchatapp.demo.model.entity.UserEntity;
import springchatapp.demo.repository.UserRepository;
import springchatapp.demo.security.AESUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository taskRepository;
  private final AESUtil aesUtil;

  public boolean authenticateUser(UserEntity userEntity) throws Exception {
    var uid = userEntity.getUid().getValue();

    var userResource = taskRepository.getUser(uid).orElse(null);
    // ユーザーが存在しない場合はエラー
    if (userResource == null) {
      return false;
    }

    String encryptedPassword = userResource.getPassword();

    return userEntity.getPassword().getValue().equals(aesUtil.decrypt(encryptedPassword));
  }


}
