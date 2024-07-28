package com.register.application.service;

import com.register.application.entity.Contact;
import com.register.application.dao.ContactDAO;
import com.register.application.exception.ContactAlreadyExistsException;
import com.register.application.exception.ContactNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
public class ContactService {

    private ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public List<Contact> findAll() {
        return contactDAO.findAll();
    }

    public Contact save(Contact contact) {
        if (contactDAO.findById(contact.getId()).isPresent()) {
            throw new ContactAlreadyExistsException();
        }
        return contactDAO.save(contact);
    }

    public Contact update(Contact contact) {
        if (contactDAO.findById(contact.getId()).isEmpty()) {
            throw new ContactNotFoundException();
        }
        return contactDAO.save(contact);
    }
}
