package springchatapp.demo.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import springchatapp.demo.mapper.TaskMapper;
import springchatapp.demo.model.resource.AddTaskResource;
import springchatapp.demo.model.resource.TaskResource;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TaskRepository {
  final private TaskMapper taskMapper;

  public List<TaskResource> getTaskList(final String uid) {
    try {
      List<TaskResource> taskResourceList = taskMapper.selectByUid(uid);
      return taskResourceList;
    } catch (Exception ex) {
      log.error("Database access error target user id: {}", uid);
      throw ex;
    }
  }

  public int addTask(AddTaskResource addTaskResource) {
    try {
      int result = taskMapper.insertTask(addTaskResource);
      return result;
    } catch (Exception ex) {
      log.error("Database access error target user id: {}", addTaskResource.getUid());
      throw ex;
    }
  }
}
