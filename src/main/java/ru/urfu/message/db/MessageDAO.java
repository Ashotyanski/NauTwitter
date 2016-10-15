package ru.urfu.message.db;


import ru.urfu.message.Message;

public interface MessageDAO {

    void addMessage(Message message);

    void updateMessage(Message message);

    Message getMessageById(Long id);

    Message[] getMessagesByAuthor(String author);

    Message[] getAllMessages();

    void deleteMessage(Long id);
}
