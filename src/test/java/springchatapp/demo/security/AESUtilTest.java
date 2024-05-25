package springchatapp.demo.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AESUtilTest {
  @Autowired
  private AESUtil target;

  @Test
  @DisplayName("パスワードを暗号化して復号化できる")
  void AESUtil_ok1() throws Exception {
    final String passwordStr = "Hoge1234";

    final String passwordEncrypt = target.encrypt(passwordStr);
    final String passwordDecrypt = target.decrypt(passwordEncrypt);

    Assertions.assertNotEquals(passwordStr, passwordEncrypt);
    Assertions.assertEquals(passwordStr, passwordDecrypt);
  }
}
