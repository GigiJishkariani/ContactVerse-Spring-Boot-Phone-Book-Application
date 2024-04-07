package dev.boot.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.boot.domain.Company;
import dev.boot.domain.Contact;
import io.swagger.v3.oas.annotations.media.Schema;
import dev.boot.domain.Person;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Schema(title = "Person", description = "Information about the Person")
public class PersonDTO {

    private final Person person;

    public PersonDTO() {
        this(new Person());
    }

    private Contact createDefaultContact(String label) {
        Contact contact = new Contact();
        contact.setLabel(label);
        return contact;
    }

    public PersonDTO(Person person){
        this.person = person;
    }

    public Person toPerson(){
        return this.person;
    }

    @Schema(description = "personId")
    public long getId() {
        return person.getId();
    }

    @Past(message = "Birthday must be in the past")
    public LocalDate getBirthday() {
        return person.getBirthday();
    }

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    public String getName() {
        return person.getName();
    }

    @NotBlank(message = "Surname is required")
    @Size(max = 255, message = "Surname must not exceed 255 characters")
    public String getSurname() {
        return person.getSurname();
    }

    public String getComment(){
        return person.getComment();
    }


    public Set<Contact> getContacts() {
        return person.getContacts();
    }

    @Schema(hidden = true)
    public void setId(long id) {
        person.setId(id);
    }

    public void setBirthday(LocalDate day) {
        person.setBirthday(day);
    }

    public void setName(String name){
        person.setName(name);
    }

    public void setSurname(String surname){
        person.setSurname(surname);
    }

    public void setComment(String comment){
        person.setComment(comment);
    }

    public void setContacts(Set<Contact> contacts){
        person.setContacts(contacts);
    }

    @JsonIgnore
    public Company getCompany(){
        return person.getCompany();
    }

    @JsonIgnore
    public void setCompany(Company company){
        person.setCompany(company);
    }

}
