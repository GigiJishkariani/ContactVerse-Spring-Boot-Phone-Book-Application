package dev.boot.service;


import dev.boot.dto.PersonDTO;
import dev.boot.repository.ContactRepository;
import dev.boot.repository.PersonRepository;
import dev.boot.domain.Contact;
import dev.boot.domain.Person;
import dev.boot.domain.ContactType;
import dev.boot.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepo;
    private final ContactRepository contactRepo;


    @Autowired
    public PersonService(PersonRepository personRepo, ContactRepository contactRepo) {
        this.personRepo = personRepo;
        this.contactRepo = contactRepo;
    }


    public PersonDTO save(PersonDTO personDTO) {
        Person person = personDTO.toPerson();

        Set<Contact> contacts = new HashSet<>();
        for (Contact contact : personDTO.getContacts()) {

            if (contact.getLabelType() == null) {
                contact.setLabelType(ContactType.CUSTOM);
            }

            contact.setPerson(person);
            contacts.add(contact);
        }

        person.setContacts(contacts);
        personRepo.save(person);
        return new PersonDTO(person);
    }




    public Optional<PersonDTO> findById(long id) {
        return personRepo.findById(id).map(PersonDTO::new);
    }

    public Iterable<PersonDTO> findAll() {
        return personRepo.findAllWithContacts().stream()
                .map(PersonDTO::new)
                .collect(Collectors.toSet());
    }


    public void delete(PersonDTO entity) {
        personRepo.delete(entity.toPerson());
    }


    public PersonDTO update(long id, PersonDTO personDTO) {
        Optional<PersonDTO> existingPerson = findById(id);

        if (existingPerson.isPresent()) {
            Person existingEntity = existingPerson.get().toPerson();
            contactRepo.deleteByPersonId(existingEntity.getId());
            existingEntity.setName(personDTO.getName());
            existingEntity.setSurname(personDTO.getSurname());
            existingEntity.setContacts(personDTO.getContacts());
            existingEntity.setComment(personDTO.getComment());
            existingEntity.setBirthday(personDTO.getBirthday());

            personDTO.getContacts().forEach(el->el.setPerson(existingEntity));
            return new PersonDTO(personRepo.save(existingEntity));
        }else {
            throw new PersonNotFoundException("Person with ID " + id + " not found.");
        }

    }



}
