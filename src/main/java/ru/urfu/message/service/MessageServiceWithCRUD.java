package ru.urfu.message.service;

import org.springframework.stereotype.Service;
import ru.urfu.message.Message;
import ru.urfu.message.db.MessageCrudRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Iterator;

@Named("crud")
@Service
public class MessageServiceWithCRUD implements MessageService {

    private final MessageCrudRepository messageCrudRepository;

    @Inject
    public MessageServiceWithCRUD(MessageCrudRepository messageCrudRepository) {
        this.messageCrudRepository = messageCrudRepository;
    }

    @Override
    public void addMessage(Message message) {
        messageCrudRepository.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageCrudRepository.findOne(id);
    }

    @Override
    public Message[] getAllMessages() {
        Message[] messages = new Message[(int) messageCrudRepository.count()];
        Iterable<Message> iterable = messageCrudRepository.findAll();
        Iterator<Message> iterator = iterable.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            messages[i] = iterator.next();
        }
        return messages;
    }

    @Override
    public void deleteMessage(Long id) {
        messageCrudRepository.delete(id);
    }
}
