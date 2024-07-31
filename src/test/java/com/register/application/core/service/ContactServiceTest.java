package com.register.application.core.service;

import com.register.application.core.dao.ContactDAO;
import com.register.application.core.entity.Contact;
import com.register.application.core.exception.ContactAlreadyExistsException;
import com.register.application.core.exception.ContactNotFoundException;
import com.register.application.security.entity.User;
import com.register.application.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;
    @Mock
    private UserService userService;
    @Mock
    private ContactDAO contactDAO;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setId(1);
        user.setUsername("fakeusername");
        when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldReturnAllContactsForCurrentUser() {
        List<Contact> contacts = List.of(new Contact());
        when(contactDAO.findAllByUser(any())).thenReturn(contacts);

        assertEquals(contacts, contactService.findAll());
    }

    @Test
    public void shouldNotSaveWhenContactAlreadyExists() {
        Contact contact = new Contact();
        contact.setId(10);
        when(contactDAO.findByUser(eq(contact.getId()), any())).thenReturn(contact);

        assertThrows(ContactAlreadyExistsException.class, () -> {
            contactService.save(contact);
        });
    }

    @Test
    public void shouldNotUpdateWhenContactIsNotFound() {
        Contact contact = new Contact();
        contact.setId(10);
        when(contactDAO.findByUser(eq(contact.getId()), any())).thenReturn(null);

        assertThrows(ContactNotFoundException.class, () -> {
            contactService.update(contact);
        });
    }

    @Test
    public void shouldNotDeleteWhenContactIsNotFound() {
        when(contactDAO.findByUser(eq(10), any())).thenReturn(null);

        assertThrows(ContactNotFoundException.class, () -> {
            contactService.delete(10);
        });
    }
}