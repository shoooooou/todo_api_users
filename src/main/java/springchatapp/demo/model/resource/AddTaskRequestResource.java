package springchatapp.demo.model.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTaskRequestResource {
  private int sequenceNo;
  private String taskName;
  private String statusCd;
}
