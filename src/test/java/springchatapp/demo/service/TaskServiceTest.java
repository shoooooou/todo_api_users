package springchatapp.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import springchatapp.demo.model.entity.UserEntityFactory;
import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserService target;

  @BeforeEach
  void setup() {
  }

  @Test
  @DisplayName("ユーザー情報を取得してログインができる")
  void getTaskList_ok1() {
    final var userResource = UserResource.builder()
        .uid("0000000001")
        .password("test1234")
        .build();
    final var userEntity = UserEntityFactory.create(userResource);

    when(userRepository.getUser(userResource.getUid())).thenReturn(Optional.of(userResource));
    when(passwordEncoder.matches(eq(userResource.getPassword()),
        any())).thenReturn(true);

    var result = target.authenticateUser(userEntity);

    verify(userRepository).getUser(userResource.getUid());
    verify(passwordEncoder).matches(eq(userResource.getPassword()), any());
    Assertions.assertTrue(result);
  }

  @Test
  @DisplayName("ユーザー情報がいない場合はログインできない")
  void getUser_ok2() {
    final var userResource = UserResource.builder()
        .uid("0000000001")
        .password("test1234")
        .build();
    final var userEntity = UserEntityFactory.create(userResource);

    when(userRepository.getUser(userResource.getUid())).thenReturn(Optional.empty());

    var result = target.authenticateUser(userEntity);

    verify(userRepository).getUser(userResource.getUid());
    verify(passwordEncoder, times(0)).matches(any(), any());

    Assertions.assertFalse(result);
  }
}
