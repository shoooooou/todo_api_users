package springchatapp.demo.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springchatapp.demo.model.entity.UserEntity;
import springchatapp.demo.model.entity.UserEntityFactory;
import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.repository.UserRepository;
import springchatapp.demo.security.AESUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private AESUtil aesUtil;
  @InjectMocks
  private UserService target;

  @BeforeEach
  void setup() {
  }

  @ParameterizedTest
  @CsvSource({
      "0000000001, test1234, true",
      "0000000002, test1234, false",
  })
  @DisplayName("ユーザー情報を取得してログインの検証ができる")
  void getTaskList_ok1(String uid, String password, boolean expected) throws Exception {
    final var userResource = createUser(uid, password);
    final var userEntity = UserEntityFactory.create(userResource);

    final String encreptedPassword = expected ? password : "different_password";
    final UserResource fetchedUserResource = createUser(uid, encreptedPassword);

    when(userRepository.getUser(userResource.getUid())).thenReturn(
        Optional.of(fetchedUserResource));
    when(aesUtil.decrypt(fetchedUserResource.getPassword())).thenReturn(
        fetchedUserResource.getPassword());

    var result = target.authenticateUser(userEntity);

    verify(userRepository).getUser(userResource.getUid());
    if (expected) {
      Assertions.assertTrue(result);
    } else {
      Assertions.assertFalse(result);
    }
  }

  @Test
  @DisplayName("ユーザー情報がいない場合はログインできない")
  void getUser_ok2() throws Exception {
    final var userResource = UserResource.builder()
        .uid("0000000001")
        .password("test1234")
        .build();
    final var userEntity = UserEntityFactory.create(userResource);

    when(userRepository.getUser(userResource.getUid())).thenReturn(Optional.empty());

    var result = target.authenticateUser(userEntity);

    verify(userRepository).getUser(userResource.getUid());
    Assertions.assertFalse(result);
  }

  @Test
  @DisplayName("ユーザ情報を登録できる")
  void registerUser_ok1() throws Exception {
    final UserResource userResource = createUser("000000001", "pass1234");
    final UserResource encryptedUserResource = createUser("000000001", "encrypted_password");
    final UserEntity userEntity = UserEntityFactory.create(userResource);

    when(aesUtil.encrypt(userEntity.getPassword().getValue())).thenReturn(
        encryptedUserResource.getPassword());
    when(userRepository.addUser(encryptedUserResource)).thenReturn(1);

    var result = target.registerUser(userEntity);

    verify(aesUtil).encrypt(userEntity.getPassword().getValue());
    verify(userRepository).addUser(encryptedUserResource);

    Assertions.assertTrue(result);
  }

  private UserResource createUser(String uid, String password) {
    return UserResource.builder()
        .uid(uid)
        .password(password)
        .build();
  }
}
