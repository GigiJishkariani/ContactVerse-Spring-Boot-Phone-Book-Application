package dev.boot.dto;

import dev.boot.domain.Contact;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NewContactorsDTO{



    private String fullName;
    private String comment;
    Set<Contact> contacts;



    public NewContactorsDTO(){
    }


}

