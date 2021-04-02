package com.simpl.paylater.model;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "user")
@Data
@Entity
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "creditLimit")
    private BigDecimal creditLimit;

    @Column(name = "usedLimit")
    private BigDecimal usedLimit = BigDecimal.ZERO;
}
