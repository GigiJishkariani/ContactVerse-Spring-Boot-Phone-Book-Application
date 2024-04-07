package dev.boot.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.boot.domain.Contact;
import dev.boot.domain.ContactType;
import dev.boot.domain.Person;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Contact", description = "Information about the contacts of the person")
public class ContactDTO {

    private final Contact contact;

    public ContactDTO(){
        this(new Contact());
    }

    public ContactDTO(Contact contact){
        this.contact = contact;
    }

    public Contact toContact(){
        return this.contact;
    }


    @Schema(description = "ID of the contact")
    public long getId() {
        return contact.getId();
    }

    public String getLabel(){
        return contact.getLabel();
    }

    public String getValue(){
        return contact.getValue();
    }


    @Schema(hidden = true)
    @JsonIgnore
    public Person getPerson(){
        return contact.getPerson();
    }

    @Schema(description = "Type of the contact label (PHONE_NUMBER, HOME_NUMBER, ADDRESS, EMAIL, CUSTOM)")
    @JsonIgnore
    public ContactType getLabelType() {
        return contact.getLabelType();
    }

    public void setLabelType(ContactType labelType) {
        contact.setLabelType(labelType);
    }


    @Schema(hidden = true)
    public void setPerson(Person person){
        contact.setPerson(person);
    }

    @Schema(hidden = true)
    public void setId(long id){
        contact.setId(id);
    }

    public void setLabel(String label){
        contact.setLabel(label);
    }

    public void setValue(String value){
        contact.setValue(value);
    }

}
