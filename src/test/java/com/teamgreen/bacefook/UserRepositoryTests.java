package com.teamgreen.bacefook;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamgreen.bacefook.entity.User;
import com.teamgreen.bacefook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository repo;

  @Test
  public void testCreateUser() {
    User user = new User();
    user.setUsername("test");
    user.setEmail("test@gmail.com");
    user.setPassword("test2021");
    user.setFirstname("Test");
    user.setLastname("Test");

    User savedUser = repo.save(user);

    User existUser = entityManager.find(User.class, savedUser.getId());

    assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

  }
}
