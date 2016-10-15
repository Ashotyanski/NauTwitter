package ru.urfu.message.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.urfu.message.Message;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class MessageCrudRepositoryTest {

    @Inject
    TestEntityManager testEntityManager;

    @Inject
    MessageCrudRepository messageCrudRepository;

    @Test
    public void testFindByAuthor() throws Exception {
        testEntityManager.persist(new Message("123", "user"));
        List<Message> messages = messageCrudRepository.findByAuthor("user");
        Assert.assertEquals(1, messages.size());
        Message message = messages.get(0);
        Assert.assertEquals("user", message.getAuthor());

        testEntityManager.persist(new Message("456", "user"));
        testEntityManager.persist(new Message("789", "user2"));
        messages = messageCrudRepository.findByAuthor("user");
        Assert.assertEquals(2, messages.size());
        message = messages.get(1);
        Assert.assertEquals("user", message.getAuthor());
    }

    @Test
    public void testNotFindByAuthor() throws Exception {
        testEntityManager.persist(new Message("123", "user1"));
        List<Message> messages = messageCrudRepository.findByAuthor("user");
        Assert.assertEquals(0, messages.size());
    }
}
