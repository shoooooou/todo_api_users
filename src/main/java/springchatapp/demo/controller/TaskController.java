package springchatapp.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springchatapp.demo.model.entity.AddTaskEntityFactory;
import springchatapp.demo.model.entity.TaskEntity;
import springchatapp.demo.model.resource.AddTaskRequestResource;
import springchatapp.demo.model.resource.TaskResource;
import springchatapp.demo.model.resource.TaskResourceFactory;
import springchatapp.demo.service.TaskService;

@RestController
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  /**
   * ユーザのタスク一覧を取得する。
   *
   * @param uid ユーザーID
   * @return タスク追加後のメッセージを含む {@link ResponseEntity}
   */
  @GetMapping("/todo/tasklist/{uid}")
  public ResponseEntity<?> getTaskList(@PathVariable String uid) {
    Optional<List<TaskEntity>> optionalTaskEntities = taskService.getTaskList(uid);

    // タスクがない場合は早期リターン
    if (optionalTaskEntities.isEmpty()) {
      return ResponseEntity.ok().body(Collections.emptyList());
    }

    List<TaskResource> taskListEntityList = new ArrayList<>();
    optionalTaskEntities.get().stream().forEach(taskEntity -> {
      taskListEntityList.add(TaskResourceFactory.create(taskEntity));
    });

    return ResponseEntity.ok().body(taskListEntityList);
  }


  /**
   * ユーザのタスクを登録する。
   *
   * @param uid ユーザーID
   * @return タスク追加後のメッセージを含む {@link ResponseEntity}
   */
  @PostMapping("/todo/tasklist/{uid}")
  public ResponseEntity<?> addTask(@PathVariable String uid,
                                   @RequestBody AddTaskRequestResource taskRequestResource) {

    final var addTaskEntity = AddTaskEntityFactory.create(uid, taskRequestResource);
    try {
      taskService.addTask(addTaskEntity);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Task added failed.");
    }

    return ResponseEntity.ok("Task added successfully.");
  }

}
