package dev.boot.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CompanyCreateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String comment;
}
