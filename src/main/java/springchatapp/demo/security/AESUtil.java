package springchatapp.demo.security;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESUtil {

  private final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private final String KEY_ALGORITHM = "AES";

  @Value("${aes.secret.key}")
  private String secretKeyString;
  @Value("${aes.init.vector}")
  private String initVectorString;

  private SecretKey secretKey;
  private IvParameterSpec iv;

  @PostConstruct
  public void init() {
    this.secretKey =
        new SecretKeySpec(secretKeyString.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);
    this.iv = new IvParameterSpec(initVectorString.getBytes(StandardCharsets.UTF_8));
  }

  // 暗号化する
  public String encrypt(String input) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(cipherText);
  }

  // 復号化する
  public String decrypt(String cipherText) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
    return new String(plainText, StandardCharsets.UTF_8);
  }
}
