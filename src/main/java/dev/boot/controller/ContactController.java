package dev.boot.controller;


import dev.boot.dto.ContactDTO;
import dev.boot.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds contact by id")
    public Optional<ContactDTO> findById(@PathVariable(name = "id") long id) {
        return contactService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds all contacts")
    public Iterable<ContactDTO> findAll() {
        return contactService.findAll();
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a contact by ID")
    public ResponseEntity<ContactDTO> update(@PathVariable(name = "id") Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updated = contactService.update(id, contactDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes contact by id")
    public void delete(@PathVariable(name = "id") long contactId){
        contactService.deleteById(contactId);
    }
}
