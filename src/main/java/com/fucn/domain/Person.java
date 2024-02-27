package com.fucn.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PERSONS", schema = "TEST_FUCN")
@DynamicUpdate
@DynamicInsert
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private String address;
    private String residence;
    private String position;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER")
    private User user;

}
