package dev.boot.dto;

import dev.boot.domain.Company;

import java.util.Set;
import java.util.stream.Collectors;

public class CompanyDTO {
    private final Company company;
    private Set<NewContactorsDTO> contactors;



    public CompanyDTO() {
        this(new Company());
    }

    public CompanyDTO(Company company) {
        this.company = company;

    }

    public Long getId() {
        return company.getId();
    }

    public String getName() {
        return company.getName();
    }

    public void setName(String name){
        company.setName(name);
    }

    public String getComment() {
        return company.getComment();
    }

    public Set<PersonDTO> getContactors() {
        return company.getContactors().stream()
                .map(PersonDTO::new).collect(Collectors.toSet());
    }




    public void setContactors(Set<NewContactorsDTO> contactors) {
        this.contactors = contactors;
    }

    public void setComment(String comment){
        company.setComment(comment);
    }


    public Company toCompany() {
        return this.company;
    }
}



