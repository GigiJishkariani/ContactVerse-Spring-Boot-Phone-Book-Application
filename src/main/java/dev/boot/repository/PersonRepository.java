package dev.boot.repository;

import dev.boot.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.contacts")
    List<Person> findAllWithContacts();
}
