package com.fucn.domain;

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
@Table(name = "DEVICES", schema = "TEST_FUCN")
@DynamicUpdate
@DynamicInsert
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDevice;
    private String name;
    private String model;
    private String reference;
    private Long quantityAvailable;


}
