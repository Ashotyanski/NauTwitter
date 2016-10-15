package ru.urfu.message.db;

import org.springframework.data.repository.CrudRepository;
import ru.urfu.message.Message;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MessageCrudRepository extends CrudRepository<Message, Long> {

    List<Message> findByAuthor(String author);
}
