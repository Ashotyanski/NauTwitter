package ru.urfu.message.service;


import org.springframework.stereotype.Service;
import ru.urfu.message.Message;
import ru.urfu.message.db.MessageDAO;

import javax.inject.Inject;
import javax.inject.Named;

@Named("dao")
@Service
public class MessageServiceWithDAO implements MessageService{

    @Inject
    private MessageDAO messageDAO;

    @Override
    public void addMessage(Message message) {
        messageDAO.addMessage(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageDAO.getMessageById(id);
    }

    @Override
    public Message[] getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public void deleteMessage(Long id) {
        messageDAO.deleteMessage(id);
    }
}
