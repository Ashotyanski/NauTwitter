package ru.urfu.user.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.urfu.message.db.MessageCrudRepository;
import ru.urfu.message.service.MessageService;
import ru.urfu.message.service.MessageServiceWithCRUD;
import ru.urfu.user.db.UserRepository;
import ru.urfu.user.model.Role;
import ru.urfu.user.model.User;
import ru.urfu.user.validation.DuplicateUserException;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class UserServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    TestEntityManager testEntityManager;

    @Inject
    UserRepository userRepository;

    private UserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new UserServiceImpl(userRepository);
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User("user1", "pass1", Role.USER);
        service.save(user);
        user = service.findByUsername("user1");
        Assert.assertEquals("user1",user.getUsername());
        Assert.assertEquals("pass1",user.getPassword());
        Assert.assertEquals(Role.USER,user.getRole());

        user = new User("user1", "pass1", Role.USER);
        thrown.expect(DuplicateUserException.class);
        service.save(user);
    }
}
