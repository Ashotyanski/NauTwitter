package ru.urfu.message.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.urfu.message.Message;
import ru.urfu.message.db.MessageCrudRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class MessageServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    TestEntityManager testEntityManager;

    @Inject
    MessageCrudRepository messageCrudRepository;

    private MessageService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new MessageServiceWithCRUD(messageCrudRepository);
    }

    @Test
    public void testGetAllMessagesReturnsEmpty() {
        Assert.assertArrayEquals(new Message[0], service.getAllMessages());
    }

    @Test
    public void testAddMessage() throws Exception {
        Message message = new Message("body", "user");
        testEntityManager.persist(message);
        Message[] messages = service.getAllMessages();
        Assert.assertEquals(1, messages.length);
        Assert.assertEquals(message, messages[0]);
    }

    @Test
    public void testDeleteMessage() throws Exception {
        Message message = new Message("body", "user");
        testEntityManager.persist(message);
        message = service.getAllMessages()[0];

        service.deleteMessage(message.getId());
        Assert.assertNull(service.getMessageById(message.getId()));
        Message[] messages = service.getAllMessages();
        Assert.assertEquals(0, messages.length);

        thrown.expect(EmptyResultDataAccessException.class);
        service.deleteMessage(message.getId());
    }
}
