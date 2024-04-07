package dev.boot.controller;


import dev.boot.dto.PersonDTO;
import dev.boot.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/People")

public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds person by id")
    public Optional<PersonDTO> findById(@PathVariable(name = "id") long id) {
        return personService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds all people")
    public Iterable<PersonDTO> findAll() {
        return personService.findAll();
    }


    @PostMapping
    @Operation(summary = "Saves Person")
    public ResponseEntity<PersonDTO> save(@RequestBody @Valid PersonDTO personDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personService.save(personDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes person by id")
    public void delete(@PathVariable(name = "id") long personId){
        findById(personId).ifPresent(personService::delete);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updates person by id")
    public PersonDTO update(@PathVariable(name = "id") long personId,@RequestBody PersonDTO personDTO){
        return personService.update(personId,personDTO);
    }


}
