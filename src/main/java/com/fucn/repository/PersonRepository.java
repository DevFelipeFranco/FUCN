package com.fucn.repository;

import com.fucn.domain.Person;
import com.fucn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsPersonByUser(User user);
}
