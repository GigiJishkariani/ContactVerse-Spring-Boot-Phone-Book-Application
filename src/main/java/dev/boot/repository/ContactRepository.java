package dev.boot.repository;

import dev.boot.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    void deleteByPersonId(Long id);
}
