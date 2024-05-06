package springchatapp.demo.repository;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import springchatapp.demo.mapper.TaskMapper;
import springchatapp.demo.model.resource.AddTaskResource;
import springchatapp.demo.model.resource.TaskResource;

@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Captor
  private ArgumentCaptor<ILoggingEvent> captorLoggingEvent;
  @Mock
  private Appender<ILoggingEvent> mockAppender;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    Logger logger = (Logger) LoggerFactory.getLogger(TaskRepository.class);
    logger.addAppender(mockAppender);
  }

  @Test
  @DatabaseSetup("/getTaskListSetup.xml")
  @DisplayName("タスク一覧が取得できる")
  void getTaskList_ok1() {
    final var expected = getExpectedTaskList();
    List<TaskResource> target = taskRepository.getTaskList("0000000001");

    Assertions.assertEquals(expected.size(), target.size());
    Assertions.assertEquals(expected, target);
  }

  @Test
  @DisplayName("タスク一覧取得処理でデータベースアクセスエラーの場合は例外をスローする")
  void getTaskList_error1() {
    final String uid = "0000000001";
    final String expectedMessage = "Database access error target user id: " + uid;

    TaskMapper mockTaskMapper = mock(TaskMapper.class);
    TaskRepository taskRepositoryWithWireMock = new TaskRepository(mockTaskMapper);
    doThrow(new RuntimeException("")).when(mockTaskMapper)
        .selectByUid(uid);

    Assertions.assertThrows(Exception.class, () -> {
      taskRepositoryWithWireMock.getTaskList(uid);
    });

    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    Assertions.assertEquals(expectedMessage,
        captorLoggingEvent.getValue().getFormattedMessage());
    Assertions.assertEquals("ERROR", captorLoggingEvent.getValue().getLevel().toString());
  }

  // TODO: テーブルの中身が空の場合しか機能しないので解消したい
  @Test
  @Transactional
  @Rollback
  @DatabaseSetup(value = "/addTaskSetup.xml", type = DatabaseOperation.CLEAN_INSERT)
  @ExpectedDatabase(value = "/addTaskExpected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  @DisplayName("タスクの登録ができる")
  void addTask_ok1() {
    final var uid = "0000000001";
    final var taskName = "todo1";
    final var statusCd = "0";

    final var addTaskResource =
        AddTaskResource.builder().uid(uid).taskName(taskName).statusCd(statusCd).build();

    int result = taskRepository.addTask(addTaskResource);
    System.out.println(result);
  }

  @Test
  @DisplayName("タスク登録処理でデータベースアクセスエラーの場合は例外をスローする")
  void addTask_error1() {
    final String uid = "0000000001";
    final AddTaskResource addTaskResource = AddTaskResource.builder().uid(uid).taskName("todo1")
        .statusCd("0").build();
    final String expectedMessage = "Database access error target user id: " + uid;

    TaskMapper mockTaskMapper = mock(TaskMapper.class);
    TaskRepository taskRepositoryWithWireMock = new TaskRepository(mockTaskMapper);
    doThrow(new RuntimeException("")).when(mockTaskMapper)
        .insertTask(addTaskResource);

    Assertions.assertThrows(Exception.class, () -> {
      taskRepositoryWithWireMock.addTask(addTaskResource);
    });

    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    Assertions.assertEquals(expectedMessage,
        captorLoggingEvent.getValue().getFormattedMessage());
    Assertions.assertEquals("ERROR", captorLoggingEvent.getValue().getLevel().toString());
  }

  private List<TaskResource> getExpectedTaskList() {
    List<TaskResource> taskList = new ArrayList<>();
    taskList.add(TaskResource.builder().sequenceNo(1234).taskName("todo1").statusCd("0").build());
    taskList.add(TaskResource.builder().sequenceNo(5678).taskName("todo2").statusCd("1").build());
    taskList.add(TaskResource.builder().sequenceNo(9876).taskName("todo3").statusCd("2").build());
    return taskList;
  }
}