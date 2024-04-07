package dev.boot.controller;


import dev.boot.dto.CompanyCreateDTO;
import dev.boot.dto.CompanyDTO;
import dev.boot.dto.NewContactorsDTO;
import dev.boot.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping
    @Operation(summary = "Create a new company")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyCreateDTO companyCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.save(companyCreateDTO));
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Find a company by ID")
    public Optional<CompanyDTO> findById(@PathVariable Long companyId) {
        return companyService.findById(companyId);
    }

    @GetMapping
    @Operation(summary = "Find all companies")
    public Iterable<CompanyDTO> findAll() {
        return companyService.findAll();
    }

    @PutMapping("/{person_id}/{company_id}")
    @Operation(summary = "Update contactors for a company")
    public ResponseEntity<Set<NewContactorsDTO>> update(@PathVariable(name = "person_id") Long personId, @PathVariable(name = "company_id") Long companyId) {
        Set<NewContactorsDTO> updated = companyService.update(personId, companyId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    @Operation(summary = "Delete a company by ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}