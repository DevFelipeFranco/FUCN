package com.fucn.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ROLES", schema = "TEST_FUCN")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRole")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    private String name;
    private String description;

//    @JsonManagedReference
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

//    @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ROLE_PERMISSION",
            joinColumns = @JoinColumn(name = "ID_ROLE"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERMISSION"))
    private Set<Permission> permissions;
}
