package ru.urfu.user.validation;


public class DuplicateUserException extends Exception{
    public DuplicateUserException() {
        super();
    }

    public DuplicateUserException(String username) {
        super(String.format("User with username \"%s\" already exists!", username));
    }

    public DuplicateUserException(String username, Throwable cause) {
        super(String.format("User with username \"%s\" already exists!", username), cause);
    }

    public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
