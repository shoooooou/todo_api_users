package springchatapp.demo.model.entity;

import springchatapp.demo.model.resource.TaskResource;
import springchatapp.demo.model.value.object.StatusCd;
import springchatapp.demo.model.value.object.TaskName;

public class TaskEntityFactory {
  public static TaskEntity create(TaskResource taskResource) {
    return TaskEntity.builder()
        .sequenceNo(taskResource.getSequenceNo())
        .taskName(TaskName.noValidateOf(taskResource.getTaskName()))
        .statusCd(StatusCd.noValidateOf(taskResource.getStatusCd()))
        .build();
  }
}
