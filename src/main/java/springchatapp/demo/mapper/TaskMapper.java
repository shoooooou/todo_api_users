package springchatapp.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import springchatapp.demo.model.resource.AddTaskResource;
import springchatapp.demo.model.resource.TaskResource;

@Mapper
public interface TaskMapper {
  List<TaskResource> selectByUid(final String uid);

  int insertTask(AddTaskResource addTaskResource);
}
