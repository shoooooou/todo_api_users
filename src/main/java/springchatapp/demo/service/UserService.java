package springchatapp.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springchatapp.demo.exceptions.DatabaseAccessException;
import springchatapp.demo.exceptions.EncryptionException;
import springchatapp.demo.model.entity.UserEntity;
import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.repository.UserRepository;
import springchatapp.demo.security.AESUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository taskRepository;
  private final AESUtil aesUtil;

  public boolean authenticateUser(UserEntity userEntity) throws EncryptionException {
    var uid = userEntity.getUid().getValue();

    var userResource = taskRepository.getUser(uid).orElse(null);
    // ユーザーが存在しない場合はエラー
    if (userResource == null) {
      return false;
    }

    String encryptedPassword = userResource.getPassword();

    return userEntity.getPassword().getValue().equals(aesUtil.decrypt(encryptedPassword));
  }


  public boolean registerUser(UserEntity userEntity)
      throws EncryptionException, DatabaseAccessException {
    final UserResource userResource = UserResource.builder()
        .uid(userEntity.getUid().getValue())
        .password(aesUtil.encrypt(userEntity.getPassword().getValue()))
        .build();

    int result = taskRepository.addUser(userResource);

    return result != 0;
  }


}
