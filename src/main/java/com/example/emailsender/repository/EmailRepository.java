package com.example.emailsender.repository;

import com.example.emailsender.data.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<Email, String> {
    List<Email> findByStatusOrStatusIsNull(String status);
}
