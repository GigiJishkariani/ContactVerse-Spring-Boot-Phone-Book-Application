package dev.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table
@Getter
@Setter
@SequenceGenerator(name = "CONTACT_ID_GEN", sequenceName = "CONTACT_ID_SEQ", allocationSize = 1)
public class Contact {

    @ManyToOne()
    @JoinColumn(name = "PERSON_ID")
    @JsonIgnore
    private Person person;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_ID_GEN")
    @JsonIgnore
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LABEL_TYPE")
    private ContactType labelType;

    @NotBlank(message = "label name is required")
    @Column(name = "LABEL_NAME")
    private String label;

    @NotBlank(message = "value is required")
    @Column(name = "VALUE")
    private String value;


    public void setLabelType(ContactType labelType) {
        this.labelType = labelType;

        if (labelType != null && labelType != ContactType.CUSTOM) {
            this.label = labelType.toString().toLowerCase();
        }
    }


}
