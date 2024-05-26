package springchatapp.demo.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springchatapp.demo.model.entity.UserEntityFactory;
import springchatapp.demo.model.resource.UserResource;
import springchatapp.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthenticateControllerTest {
  @Mock
  UserService UserService;
  private MockMvc mockMvc;
  @InjectMocks
  private AuthenticateController target;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(target).build();
  }

  @DisplayName("ログイン認証が成功したらtrueが返却される")
  @Test
  void loginAuthenticate_ok1() throws Exception {
    final var uid = "0000000001";
    final var password = "test1234";

    var userEntity =
        UserEntityFactory.create(UserResource.builder().uid(uid).password(password).build());

    when(UserService.authenticateUser(any())).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.post("/todo/authenticate/login")
            .contentType("application/json")
            .content("{\"uid\":\"" + uid + "\",\"password\":\"" + password + "\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));

  }

  @DisplayName("ログイン認証の成功可否に応じた値が返却される")
  @ParameterizedTest
  @CsvSource({
      "true",
      "false"
  })
  void registerUser_ok1(final boolean expected) throws Exception {
    final var uid = "0000000001";
    final var password = "test1234";

    when(UserService.registerUser(any())).thenReturn(expected);

    mockMvc.perform(MockMvcRequestBuilders.post("/todo/authenticate/register")
            .contentType("application/json")
            .content("{\"uid\":\"" + uid + "\",\"password\":\"" + password + "\"}"))
        .andExpect(expected ? status().isOk() : status().isBadRequest())
        .andExpect(content().string(expected ? "true" : "false"));

  }


}