package com.fucn.repository;

import com.fucn.domain.Loan;
import com.fucn.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findLoanByPerson(Person person);
}
