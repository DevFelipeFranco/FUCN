package com.fucn.controller.loan;

import com.fucn.domain.Loan;
import com.fucn.dto.HttpResponse;
import com.fucn.dto.LoanDTO;
import com.fucn.dto.PersonDTO;
import com.fucn.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping(value = "/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping(value = "/create-loan")
    public ResponseEntity<HttpResponse> createPerson(@RequestBody LoanDTO loanDTO) {
        Loan loan = loanService.createLoan(loanDTO);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("loan", loan))
                        .message(String.format("Loan created for person %s", loan.getPerson().getFirstName()))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
