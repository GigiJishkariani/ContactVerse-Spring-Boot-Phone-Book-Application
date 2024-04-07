package dev.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@SequenceGenerator(name = "PERSON_ID_GEN", sequenceName = "PERSON_ID_SEQ", allocationSize = 1)
public class Person{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ID_GEN")
    @Column(name = "PERSON_ID")
    private long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Surname is required")
    @Column(nullable = false)
    private String surname;

    @Column()
    private LocalDate birthday;

    @NotBlank(message = "Comment is required")
    @Column(nullable = false)
    private String comment;


    public Person(){

    }

    public Person(long id, String name, String surname, LocalDate birthday, String comment) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.comment = comment;
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

}