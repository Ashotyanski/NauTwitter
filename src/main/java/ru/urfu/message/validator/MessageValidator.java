package ru.urfu.message.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.urfu.message.Message;

@Component
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Message.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Message message = (Message) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "NotEmpty");
        if (message.getBody().length() > 140) {
            errors.rejectValue("body", "Size.message.body");
        }

    }
}