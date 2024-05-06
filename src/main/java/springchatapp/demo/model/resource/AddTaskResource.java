package springchatapp.demo.model.resource;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddTaskResource {
  private int sequenceNo;
  private String uid;
  private String taskName;
  private String statusCd;
}
