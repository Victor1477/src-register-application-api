package com.register.application.core.controller;

import com.register.application.core.dto.ErrorResponseDTO;
import com.register.application.core.entity.Contact;
import com.register.application.core.service.ContactService;
import com.register.application.core.exception.ContactAlreadyExistsException;
import com.register.application.core.exception.ContactNotFoundException;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Resource(name = "contactService")
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {
        try {
            return ResponseEntity.ok(contactService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Contact contact) {
        try {
            return ResponseEntity.ok(contactService.save(contact));
        } catch (ContactAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(String.format("Contact with id: %s already exists", contact.getId()), LocalDateTime.now()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Contact contact) {
        try {
            return ResponseEntity.ok(contactService.update(contact));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponseDTO(String.format("Contact not found for given id: %s", contact.getId()), LocalDateTime.now()));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            contactService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ContactNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponseDTO(String.format("Contact not found for given id: %s", id), LocalDateTime.now()));
        }
    }
}
