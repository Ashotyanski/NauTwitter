package ru.urfu.user.db;

import org.springframework.data.repository.CrudRepository;
import ru.urfu.user.model.User;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
