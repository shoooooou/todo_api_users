package springchatapp.demo.repository;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import springchatapp.demo.mapper.UserMapper;

@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

  @Autowired
  private UserRepository target;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Captor
  private ArgumentCaptor<ILoggingEvent> captorLoggingEvent;
  @Mock
  private Appender<ILoggingEvent> mockAppender;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    Logger logger = (Logger) LoggerFactory.getLogger(UserRepository.class);
    logger.addAppender(mockAppender);
  }

  @Test
  @DatabaseSetup("/getUserSetup.xml")
  @DisplayName("ユーザが取得できる")
  void getUser_ok1() {
    final var uid = "0000000001";
    final var password = "123456789";
    var result = target.getUser(uid);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(uid, result.get().getUid());
    Assertions.assertEquals(password, result.get().getPassword());
  }

  @Test
  @DatabaseSetup("/getUserSetup.xml")
  @DisplayName("ユーザが取得できない場合はnullを返す")
  void getUser_ok2() {
    final var uid = "0000000004";
    final var result = target.getUser(uid);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  @DatabaseSetup("/getUserSetup.xml")
  @DisplayName("データベースエラーの場合は例外をスローする")
  void getUser_error1() {
    final var uid = "0000000001";
    final var expectedMessage = "Database access error target user id: " + uid;
    UserMapper mockUserMapper = mock(UserMapper.class);
    doThrow(new RuntimeException("")).when(mockUserMapper)
        .selectByUserName(uid);
    UserRepository userRepositoryWithWireMock = new UserRepository(mockUserMapper);


    Assertions.assertThrows(Exception.class, () -> {
      userRepositoryWithWireMock.getUser(uid);
    });
    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    Assertions.assertEquals(expectedMessage,
        captorLoggingEvent.getValue().getFormattedMessage());
    Assertions.assertEquals("ERROR", captorLoggingEvent.getValue().getLevel().toString());
  }

}