package ru.urfu.user.db;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.urfu.message.Message;
import ru.urfu.message.db.MessageCrudRepository;
import ru.urfu.user.model.Role;
import ru.urfu.user.model.User;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class UserRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    TestEntityManager testEntityManager;

    @Inject
    UserRepository userRepository;

    @Test
    public void testFindByAuthor() throws Exception {
        testEntityManager.persist(new User("user1", "pass1", Role.USER));
        User user = userRepository.findByUsername("user1");
        Assert.assertEquals("user1", user.getUsername());
        Assert.assertEquals("pass1", user.getPassword());

        testEntityManager.persist(new User("user1", "pass1", Role.USER));
        thrown.expect(IncorrectResultSizeDataAccessException.class);
        user = userRepository.findByUsername("user1");
    }

    @Test
    public void testNotFindByAuthor() throws Exception {
        testEntityManager.persist(new Message("123", "user1"));
        User user = userRepository.findByUsername("user");
        Assert.assertNull(user);
    }

}
