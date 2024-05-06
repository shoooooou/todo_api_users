package springchatapp.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springchatapp.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository taskRepository;


}
