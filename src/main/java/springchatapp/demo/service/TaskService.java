package springchatapp.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springchatapp.demo.model.entity.AddTaskEntity;
import springchatapp.demo.model.entity.TaskEntity;
import springchatapp.demo.model.entity.TaskEntityFactory;
import springchatapp.demo.model.resource.AddTaskResourceFactory;
import springchatapp.demo.model.resource.TaskResource;
import springchatapp.demo.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;

  public Optional<List<TaskEntity>> getTaskList(final String uid) {
    List<TaskResource> listTaskResource = taskRepository.getTaskList(uid);

    // タスクがない場合は早期リターン
    if (listTaskResource.isEmpty()) {
      return Optional.empty();
    }

    List<TaskEntity> listTaskEntity = new ArrayList<>();
    listTaskResource.stream().forEach(taskResource -> {
      listTaskEntity.add(TaskEntityFactory.create(taskResource));
    });

    return Optional.of(listTaskEntity);
  }

  public int addTask(AddTaskEntity addTaskEntity) {

    return taskRepository.addTask(AddTaskResourceFactory.create(addTaskEntity));
  }
}
