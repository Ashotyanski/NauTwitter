package ru.urfu.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.urfu.message.service.MessageService;
import ru.urfu.message.validator.MessageValidator;
import ru.urfu.user.model.User;
import ru.urfu.user.service.UserService;
import ru.urfu.user.validation.UserValidator;

import javax.inject.Inject;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Inject
    private MockMvc mvc;

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    UserValidator userValidator;

    @MockBean
    private UserService userService;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("username", "password");

        given(userService.findByUsername("username")).willReturn(user);
        given(userService.findByUsername("username2")).willReturn(null);
        given(userService.findByUsername("user")).willReturn(null);
        given(userService.findByUsername("usernameusernameusernameusernameu")).willReturn(null);
    }

    @Test
    public void testPostRegister() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("username", "username2");
        valueMap.add("password", user.getPassword());

        mvc.perform(post("/register").params(valueMap))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testPostRegisterWithDuplicateUsername() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("username", "username");
        valueMap.add("password", user.getPassword());
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "Duplicate.userForm.username"));
    }

    @Test
    public void testPostRegisterWithTooShortOrLongUsername() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("username", "user");
        valueMap.add("password", user.getPassword());
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "Size.userForm.username"));

        valueMap.set("username", "usernameusernameusernameusernameu");
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "Size.userForm.username"));
    }

    @Test
    public void testPostRegisterWithTooShortOrLongPassword() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("username", user.getPassword());
        valueMap.add("password", "pass");
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Size.userForm.password"));

        valueMap.set("password", "passwordpasswordpasswordpasswordp");
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Size.userForm.password"));
    }

    @Test
    public void testPostRegisterWithEmptyPasswordOrUsername() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("username", "");
        valueMap.add("password", user.getPassword());
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotEmpty"));

        valueMap.set("username", user.getUsername());
        valueMap.set("password", "");
        mvc.perform(post("/register").params(valueMap))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "NotEmpty"));
    }


}
