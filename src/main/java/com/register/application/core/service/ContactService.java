package com.register.application.core.service;

import com.register.application.core.entity.Contact;
import com.register.application.core.dao.ContactDAO;
import com.register.application.core.exception.ContactAlreadyExistsException;
import com.register.application.core.exception.ContactNotFoundException;
import com.register.application.security.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
public class ContactService {

    private UserService userService;
    private ContactDAO contactDAO;

    public ContactService(UserService userService, ContactDAO contactDAO) {
        this.userService = userService;
        this.contactDAO = contactDAO;
    }

    public List<Contact> findAll() {
        return contactDAO.findAllByUser(userService.getCurrentUser());
    }

    public Contact save(Contact contact) {
        if (contactDAO.findByUser(contact.getId(), userService.getCurrentUser()) != null) {
            throw new ContactAlreadyExistsException();
        }
        contact.setUser(userService.getCurrentUser());
        return contactDAO.save(contact);
    }

    public Contact update(Contact contact) {
        if (contactDAO.findByUser(contact.getId(), userService.getCurrentUser()) == null) {
            throw new ContactNotFoundException();
        }
        contact.setUser(userService.getCurrentUser());
        return contactDAO.save(contact);
    }

    public void delete(Integer id) {
        Contact contact = contactDAO.findByUser(id, userService.getCurrentUser());
        if (contact == null) {
            throw new ContactNotFoundException();
        }
        contactDAO.delete(contact);
    }
}
