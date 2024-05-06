//package springchatapp.demo.service;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import springchatapp.demo.model.entity.AddTaskEntityFactory;
//import springchatapp.demo.model.entity.TaskEntity;
//import springchatapp.demo.model.entity.TaskEntityFactory;
//import springchatapp.demo.model.resource.AddTaskRequestResource;
//import springchatapp.demo.model.resource.AddTaskResourceFactory;
//import springchatapp.demo.model.resource.TaskResource;
//import springchatapp.demo.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class TaskServiceTest {
//  @Mock
//  private UserRepository taskRepository;
//  @InjectMocks
//  private UserService target;
//
//  @BeforeEach
//  void setup() {
//  }
//
//  @Test
//  @DisplayName("タスクが取得できない場合は空のOptionalで返す")
//  void getTaskList_ok1() {
//    final String uid = "00000000000000000001";
//
//    var result = target.getTaskList(uid);
//
//    Assertions.assertTrue(result.isEmpty(), "空のOptionalで返ってきている");
//  }
//
//  @Test
//  @DisplayName("Uidに紐づいたタスク一覧のEntityが取得できる")
//  void getTaskList_ok2() {
//    final String uid = "00000000000000000001";
//    final List<TaskEntity> expected = createExpected();
//
//    when(taskRepository.getTaskList(anyString())).thenReturn(createTaskListResource());
//
//    var result = target.getTaskList(uid);
//
//    Assertions.assertEquals(expected.toArray().length, result.get().toArray().length);
//    for (int i = 0; i < expected.toArray().length; i++) {
//      Assertions.assertEquals(expected.get(i).getSequenceNo(),
//          result.get().get(i).getSequenceNo());
//      Assertions.assertEquals(expected.get(i).getTaskName(),
//          result.get().get(i).getTaskName());
//      Assertions.assertEquals(expected.get(i).getStatusCd(),
//          result.get().get(i).getStatusCd());
//    }
//  }
//
//  @Test
//  @DisplayName("タスクの登録ができる")
//  void addTask_ok1() {
//    final var sequenceNo = 1;
//    final var uid = "00000000000000000001";
//    final var taskName = "task1";
//    final var statusCd = "1";
//    final var addTaskRequestResource =
//        AddTaskRequestResource.builder().sequenceNo(sequenceNo).taskName(taskName)
//            .statusCd(statusCd).build();
//
//    final var addTaskEntity = AddTaskEntityFactory.create(uid, addTaskRequestResource);
//    final var addTaskResource = AddTaskResourceFactory.create(addTaskEntity);
//
//    when(taskRepository.addTask(addTaskResource)).thenReturn(1);
//
//    var result = target.addTask(addTaskEntity);
//
//    Assertions.assertEquals(1, result);
//    verify(taskRepository).addTask(addTaskResource);
//
//  }
//
//  private List<TaskResource> createTaskListResource() {
//    List<TaskResource> taskList = new ArrayList<>();
//
//    taskList.add(TaskResource.builder().sequenceNo(1).taskName("task1").statusCd("1").build());
//    taskList.add(TaskResource.builder().sequenceNo(2).taskName("task2").statusCd("2").build());
//    taskList.add(TaskResource.builder().sequenceNo(3).taskName("task3").statusCd("3").build());
//    taskList.add(TaskResource.builder().sequenceNo(4).taskName("task4").statusCd("1").build());
//    taskList.add(TaskResource.builder().sequenceNo(5).taskName("task5").statusCd("2").build());
//
//    return taskList;
//  }
//
//  private List<TaskEntity> createExpected() {
//    final List<TaskEntity> expected = new ArrayList<>();
//    createTaskListResource().stream().forEach(taskResource -> {
//      expected.add(TaskEntityFactory.create(taskResource));
//    });
//    return expected;
//  }
//}
