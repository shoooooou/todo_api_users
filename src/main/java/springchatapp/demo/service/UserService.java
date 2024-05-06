package springchatapp.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springchatapp.demo.model.entity.UserEntity;
import springchatapp.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository taskRepository;
  final private PasswordEncoder passwordEncoder;

  public boolean authenticateUser(UserEntity userEntity) {
    var uid = userEntity.getUid().getValue();

    var fetchUserResource = taskRepository.getUser(uid).orElse(null);
    // ユーザーが存在しない場合はエラー
    if (fetchUserResource == null) {
      return false;
    }

    var requestPassword = userEntity.getPassword().getValue();

    // TODO: 必要なくなったら消す
    log.info("rowPassword:" + userEntity.getPassword().getValue());
    log.info("encodedPassword:" + passwordEncoder.encode(userEntity.getPassword().getValue()));

    return passwordEncoder.matches(requestPassword, fetchUserResource.getPassword());
  }


}
