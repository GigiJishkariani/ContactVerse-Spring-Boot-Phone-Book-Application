package dev.boot.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import javax.validation.constraints.NotBlank;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter
@Setter
@SequenceGenerator(name = "COMPANY_ID_GEN", sequenceName = "COMPANY_ID_SEQ", allocationSize = 1)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_ID_GEN")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column
    private String name;

    @Column
    private String comment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Person> contactors;


}
