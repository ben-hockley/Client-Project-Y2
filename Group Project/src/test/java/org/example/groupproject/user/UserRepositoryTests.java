package org.example.groupproject.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.groupproject.applicant.user.User;
import org.example.groupproject.applicant.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class
UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;
    @Autowired
    private JdbcClient jdbcClient;

    @MockBean
    private JavaMailSender mailSender;

    // test methods go below

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("Ben1234");
        user.setPassword("password");
        user.setEmail("ben@gmail.com");
        user.setIsAdmin(Boolean.TRUE);

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
}
