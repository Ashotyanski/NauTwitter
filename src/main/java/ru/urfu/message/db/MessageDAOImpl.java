package ru.urfu.message.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.message.Message;

import java.util.List;

@Component
@Repository
@Transactional
public class MessageDAOImpl implements MessageDAO {

    private SessionFactory sessionFactory;

    public MessageDAOImpl() {
//        this.sessionFactory = new SessionFactoryImpl();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addMessage(Message message) {
        Session session = sessionFactory.openSession();
        session.save(message);
        session.flush();
    }

    @Override
    public void updateMessage(Message message) {
        Session session = sessionFactory.openSession();
        session.update(message);
        session.flush();
    }

    @Override
    public Message getMessageById(Long id) {
        Session session = sessionFactory.openSession();
        Message message = session.get(Message.class, id);
        session.flush();
        return message;
    }

    @Override
    public Message[] getMessagesByAuthor(String author) {
        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("from messages");
        List<Message> messagesList = session.createCriteria(Message.class)
                .add(Restrictions.eq("author", author))
                .list();
        Message[] messages = new Message[messagesList.size()];
        messagesList.toArray(messages);
        session.flush();
        return messages;
    }

    @Override
    public Message[] getAllMessages() {
        Session session = sessionFactory.openSession();
        List<Message> messagesList = session.createCriteria(Message.class)
                .list();
        Message[] messages = new Message[messagesList.size()];
        messagesList.toArray(messages);
        session.flush();
        return messages;
    }

    @Override
    public void deleteMessage(Long id) {
        Session session = sessionFactory.openSession();
        Message message = session.get(Message.class, id);
        if (message != null) {
            session.delete(message);
            session.flush();
            return;
        }
        throw new RuntimeException("Message could not be deleted: there is no message with id = " + id);
    }
}
