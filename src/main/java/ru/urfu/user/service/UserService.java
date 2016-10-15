package ru.urfu.user.service;

import ru.urfu.user.model.User;
import ru.urfu.user.validation.DuplicateUserException;

public interface UserService {
    void save(User user) throws DuplicateUserException;

    User findByUsername(String username);
}
