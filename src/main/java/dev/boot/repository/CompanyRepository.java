package dev.boot.repository;

import dev.boot.domain.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.contactors")
    Set<Company> findAllWithContactors();

}
