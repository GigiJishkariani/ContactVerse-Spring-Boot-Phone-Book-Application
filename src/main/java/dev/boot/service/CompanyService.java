package dev.boot.service;

import dev.boot.dto.CompanyCreateDTO;
import dev.boot.dto.CompanyDTO;
import dev.boot.dto.NewContactorsDTO;
import dev.boot.dto.PersonDTO;
import dev.boot.repository.PersonRepository;
import dev.boot.domain.Contact;
import dev.boot.domain.ContactType;
import dev.boot.domain.Person;
import dev.omedia.boot.dto.*;
import dev.boot.exceptions.CompanyNotFoundException;
import dev.boot.exceptions.PersonNotFoundException;
import dev.boot.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.boot.domain.Company;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyService {
    private final CompanyRepository companyRepo;
    private final PersonRepository personRepo;


    @Autowired
    public CompanyService(CompanyRepository companyRepo, PersonRepository personRepo) {
        this.companyRepo = companyRepo;
        this.personRepo = personRepo;
    }


    private NewContactorsDTO personDTOToContactors(PersonDTO personDTO) {
        NewContactorsDTO newContactorsDTO = new NewContactorsDTO();
        newContactorsDTO.setFullName(personDTO.getName() + " " + personDTO.getSurname());
        newContactorsDTO.setComment(personDTO.getComment());
        Set<Contact> contacts = personDTO.getContacts();
        contacts = contacts.stream().filter(contact ->
                contact.getLabelType() == ContactType.EMAIL ||
                        contact.getLabelType() == ContactType.PHONE_NUMBER).collect(Collectors.toSet());

        newContactorsDTO.setContacts(contacts);
        return newContactorsDTO;

    }



    public Set<NewContactorsDTO> update(Long personId, long companyId) {
        Optional<CompanyDTO> companyDTO = findById(companyId);
        if (companyDTO.isPresent()){
            Company company = companyDTO.get().toCompany();
            Optional<Person> personDTO1 = personRepo.findById(personId);
            if (personDTO1.isPresent()){
                Person person = personDTO1.get();
                company.getContactors().add(person);
                person.setCompany(company);
                Company savedCompany = companyRepo.save(company);
                CompanyDTO saved = new CompanyDTO(savedCompany);

                Set<NewContactorsDTO> contactorDTOs = company.getContactors().stream()
                        .map(person1 -> {
                            PersonDTO p = new PersonDTO(person1);
                            p.setCompany(company);
                            p.setComment(person1.getComment());
                            p.setBirthday(person1.getBirthday());
                            p.setName(person.getName());
                            p.setContacts(person1.getContacts());
                            p.setSurname(person1.getSurname());
                            p.setId(person1.getId());
                            return personDTOToContactors(p);
                        }).collect(Collectors.toSet());


                saved.setContactors(contactorDTOs);
                saved.getContactors().
                        forEach(personDTO -> {
                            Person p = personDTO.toPerson();
                            p.getContacts().forEach(contact -> contact.setPerson(p));
                            p.setCompany(company);
                            personRepo.save(p);

                        });
                return contactorDTOs;
            }
            else throw new PersonNotFoundException("Person with ID " + personId + " not found.");

        }
        else throw new CompanyNotFoundException("Company with ID " + companyId + " not found.");

    }

    public CompanyDTO save(CompanyCreateDTO companyCreateDTO) {
        String name = companyCreateDTO.getName();
        String comment = companyCreateDTO.getComment();
        Company company = new Company();
        company.setName(name);
        company.setComment(comment);
        company.setContactors(new HashSet<>());

        return new CompanyDTO(companyRepo.save(company));

    }


    public Optional<CompanyDTO> findById(Long companyId) {
        return companyRepo.findById(companyId).map(CompanyDTO::new);

    }



    public Iterable<CompanyDTO> findAll() {
        return StreamSupport.stream(companyRepo.findAll().spliterator(), false)
                .map(CompanyDTO::new)
                .collect(Collectors.toSet());
    }



    public void deleteCompany(Long companyId) {
        Optional<CompanyDTO> companyDTO = findById(companyId);
        if (companyDTO.isPresent()){
            Company company = companyDTO.get().toCompany();
            company.getContactors()
                    .forEach(person -> {
                        person.setCompany(null);
                    });
            company.setContactors(null);
            companyRepo.deleteById(companyId);
        }
    }
}