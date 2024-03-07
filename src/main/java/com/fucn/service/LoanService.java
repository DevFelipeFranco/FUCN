package com.fucn.service;

import com.fucn.domain.Loan;
import com.fucn.domain.Person;
import com.fucn.dto.LoanDTO;
import com.fucn.dto.PersonDTO;

public interface LoanService {

    Loan createLoan(LoanDTO loanDTO);
}
