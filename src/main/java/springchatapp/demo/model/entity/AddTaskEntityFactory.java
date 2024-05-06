package springchatapp.demo.model.entity;

import springchatapp.demo.model.resource.AddTaskRequestResource;
import springchatapp.demo.model.resource.AddTaskResource;
import springchatapp.demo.model.value.object.StatusCd;
import springchatapp.demo.model.value.object.TaskName;
import springchatapp.demo.model.value.object.Uid;

public class AddTaskEntityFactory {
  public static AddTaskEntity create(String uid, AddTaskRequestResource addTaskRequestResource) {

    return AddTaskEntity.builder()
        .sequenceNo(addTaskRequestResource.getSequenceNo())
        .uid(Uid.noValidateOf(uid))
        .taskName(TaskName.noValidateOf(addTaskRequestResource.getTaskName()))
        .statusCd(StatusCd.noValidateOf(addTaskRequestResource.getStatusCd()))
        .build();
  }

  public static AddTaskEntity create(AddTaskResource addTaskResource) {

    return AddTaskEntity.builder()
        .sequenceNo(addTaskResource.getSequenceNo())
        .uid(Uid.noValidateOf(addTaskResource.getUid()))
        .taskName(TaskName.noValidateOf(addTaskResource.getTaskName()))
        .statusCd(StatusCd.noValidateOf(addTaskResource.getStatusCd()))
        .build();
  }
}
