package ru.urfu.user.service;

import org.springframework.stereotype.Service;
import ru.urfu.user.model.User;
import ru.urfu.user.db.UserRepository;
import ru.urfu.user.validation.DuplicateUserException;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) throws DuplicateUserException {
        if (findByUsername(user.getUsername()) != null) {
            throw new DuplicateUserException(user.getUsername());
        }
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
