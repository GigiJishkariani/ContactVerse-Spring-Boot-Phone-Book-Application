package dev.boot.service;


import dev.boot.dto.ContactDTO;
import dev.boot.repository.ContactRepository;
import dev.boot.repository.PersonRepository;
import dev.boot.domain.Contact;
import dev.boot.domain.Person;
import dev.boot.exceptions.ContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContactService {

    private final ContactRepository contactRepo;
    private final PersonRepository personRepo;

    @Autowired
    public ContactService(ContactRepository contactRepo, PersonRepository personRepo){
        this.contactRepo = contactRepo;
        this.personRepo = personRepo;
    }

    public ContactDTO save(ContactDTO contactDTO) {
        Contact save = contactRepo.save(contactDTO.toContact());
        return new ContactDTO(save);
    }


    public ContactDTO update(Long id, ContactDTO contactDTO){
        Optional<ContactDTO> existingContact = findById(id);

        if (existingContact.isPresent()) {
            ContactDTO existingEntity = existingContact.get();
            existingEntity.setLabel(contactDTO.getLabel());
            existingEntity.setValue(contactDTO.getValue());
            return new ContactDTO(save(existingEntity).toContact());
        } else {
            throw new ContactNotFoundException("Contact with ID " + id + " not found.");
        }

    }

    public Optional<ContactDTO> findById(long id) {
        return contactRepo.findById(id).map(ContactDTO::new);
    }

    public Iterable<ContactDTO> findAll() {
        return StreamSupport.stream(contactRepo.findAll().spliterator(), false)
                .map(ContactDTO::new)
                .collect(Collectors.toSet());
    }

    public void delete(ContactDTO entity) {
        contactRepo.delete(entity.toContact());
    }

    public void deleteById(long id){
        Optional<Contact> contactOptional = contactRepo.findById(id);

        if (contactOptional.isPresent()) {
            Contact contactToDelete = contactOptional.get();
            Person person = contactToDelete.getPerson();
            contactToDelete.setPerson(null);
            person.getContacts().remove(contactToDelete);
            contactRepo.delete(contactToDelete);
        }else throw new ContactNotFoundException("Contact with ID " + id + " not found.");

    }


}
