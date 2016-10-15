package ru.urfu.message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.urfu.message.service.MessageService;
import ru.urfu.message.validator.MessageValidator;

import javax.inject.Inject;
import javax.inject.Named;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Inject
    private MockMvc mvc;

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    MessageValidator messageValidator;

    @MockBean(name = "crud")
    MessageService messageService;

    @Before
    public void setUp() throws Exception {
        Message message1 = new Message("Lorem ipsum", "user1");
        Message message2 = new Message("Dolor sit amet", "user2");
        given(messageService.getMessageById(1L))
                .willReturn(message1);
        given(messageService.getMessageById(2L))
                .willReturn(null);
        given(messageService.getAllMessages())
                .willReturn(new Message[]{message1, message2});
    }

    @Test
    @WithMockUser(username = "user1", password = "pass1")
    public void testShowMessage() throws Exception {
        given(messageService.getMessageById(1L))
                .willReturn(new Message("Lorem ipsum", "user1"));

        mvc.perform(get("/message/1"))
                .andExpect(status().isOk())
                .andExpect(
                        model().attribute("message", allOf(
                                hasProperty("body", is("Lorem ipsum")),
                                hasProperty("author", is("user1"))
                                )
                        )
                );

        mvc.perform(get("/message/2"))
                .andExpect(redirectedUrl("/error"));
    }

    @Test
    @WithMockUser(username = "user1", password = "pass1", authorities = {"ADMIN"})
    public void testRenderAllMessages() throws Exception {
        mvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(
                        model().attribute("messages", hasItemInArray(
                                allOf(
                                        hasProperty("body", is("Lorem ipsum")),
                                        hasProperty("author", is("user1"))
                                )
                                )
                        )
                )
                .andExpect(
                        model().attribute("messages", hasItemInArray(
                                allOf(
                                        hasProperty("body", is("Dolor sit amet")),
                                        hasProperty("author", is("user2"))
                                )
                        ))
                )
                .andExpect(
                        model().attribute("isAdmin", is(Boolean.TRUE))
                );

    }

    @Test
    public void testDeleteMessage() throws Exception {
        mvc.perform(get("/message/delete?id=1").with(user("user1")))
                .andExpect(redirectedUrl("/messages"));
        mvc.perform(get("/message/delete?id=2").with(user("user1")))
                .andExpect(redirectedUrl("/error"));
    }

    @Test
    public void testDeleteMessageAsAdmin() throws Exception {
        mvc.perform(get("/message/delete?id=1").
                with(user(
                        new User("admin", "admin",
                                Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
                        )
                )))
                .andExpect(redirectedUrl("/messages"));
    }

    @Test
    public void testDeleteMessageAsNotAuthorizedUser() throws Exception {
        mvc.perform(get("/message/delete?id=1").with(user("user2")))
                .andExpect(redirectedUrl("/error"));
    }

    @Test
    public void testCreateMessage() throws Exception {
        mvc.perform(post("/message/create").param("body", "Message").with(user("user1")))
                .andExpect(redirectedUrl("/messages"));
    }

    @Test
    public void testCreateMessageWithEmptyBody() throws Exception {
        mvc.perform(post("/message/create").param("body", "").with(user("user1")))
                .andExpect(model().attributeHasFieldErrorCode("message", "body", "NotEmpty"));
    }

    @Test
    public void testCreateMessageWithCharacterOverflow() throws Exception {
        //141 characters
        String bigString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        mvc.perform(post("/message/create").param("body", bigString).with(user("user1")))
                .andExpect(model().attributeHasFieldErrorCode("message", "body", "Size.message.body"));
    }

}
