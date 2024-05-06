package springchatapp.demo.model.entity;

import lombok.Builder;
import lombok.Data;
import springchatapp.demo.model.value.object.StatusCd;
import springchatapp.demo.model.value.object.TaskName;
import springchatapp.demo.model.value.object.Uid;

@Builder
@Data
public class AddTaskEntity {
  private int sequenceNo;
  private Uid uid;
  private StatusCd statusCd;
  private TaskName taskName;
}
