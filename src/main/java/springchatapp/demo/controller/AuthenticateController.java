package springchatapp.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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


//  /**
//   * ユーザのタスクを登録する。
//   *
//   * @param uid ユーザーID
//   * @return タスク追加後のメッセージを含む {@link ResponseEntity}
//   */
//  @PostMapping("/todo/tasklist/{uid}")
//  public ResponseEntity<?> addTask(@PathVariable String uid,
//                                   @RequestBody AddTaskRequestResource taskRequestResource) {
//
//    final var addTaskEntity = AddTaskEntityFactory.create(uid, taskRequestResource);
//    try {
//      taskService.addTask(addTaskEntity);
//    } catch (Exception e) {
//      return ResponseEntity.status(500).body("Task added failed.");
//    }
//
//    return ResponseEntity.ok("Task added successfully.");
//  }

}
