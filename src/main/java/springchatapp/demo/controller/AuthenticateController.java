package springchatapp.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springchatapp.demo.exceptions.EncryptionException;
import springchatapp.demo.model.entity.UserEntity;
import springchatapp.demo.model.entity.UserEntityFactory;
import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthenticateController {
  private final UserService taskService;

  /**
   * ログイン認証を行う。
   *
   * @return ログイン成功/失敗 {@link ResponseEntity}
   */
  @PostMapping("/todo/authenticate/login")
  public ResponseEntity<?> loginAuthenticate(@RequestBody UserResource userResource)
      throws Exception {

    final UserEntity userEntity = UserEntityFactory.create(userResource);
    final boolean isAuthenticate = taskService.authenticateUser(userEntity);

    return ResponseEntity.ok().body(isAuthenticate);
  }

  /**
   * ユーザの情報を登録する。
   *
   * @return ユーザ登録成功/失敗
   */
  @PostMapping("/todo/authenticate/register")
  public ResponseEntity<?> registerUser(@RequestBody UserResource userResource)
      throws EncryptionException {
    final UserEntity userEntity = UserEntityFactory.create(userResource);
    final boolean isRegister = taskService.registerUser(userEntity);

    return isRegister ? ResponseEntity.ok().body("true") :
        ResponseEntity.badRequest().body("false");
  }

}
