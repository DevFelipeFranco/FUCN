package com.fucn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LOANS", schema = "TEST_FUCN")
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties({"person"})
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;
    private String description;
    private String address;

    @OneToOne
    @JoinColumn(name = "ID_REQUEST_TYPE")
    private RequestType requestType;

    @ManyToOne
    @JoinColumn(name = "ID_PERSON")
    private Person person;
}
