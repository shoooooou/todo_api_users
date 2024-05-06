package springchatapp.demo.model.resource;

import springchatapp.demo.model.entity.AddTaskEntity;

public class AddTaskResourceFactory {
  public static AddTaskResource create(AddTaskEntity AddtaskEntity) {

    return AddTaskResource.builder()
        .sequenceNo(AddtaskEntity.getSequenceNo())
        .uid(AddtaskEntity.getUid().getValue())
        .statusCd(AddtaskEntity.getStatusCd().getValue())
        .taskName(AddtaskEntity.getTaskName().getValue())
        .build();
  }
}
