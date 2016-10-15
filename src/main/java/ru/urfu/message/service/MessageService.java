package ru.urfu.message.service;

import ru.urfu.message.Message;

public interface MessageService {

    void addMessage(Message message);

    Message getMessageById(Long id);

    Message[] getAllMessages();

    void deleteMessage(Long id);
}
