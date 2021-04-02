package com.simpl.paylater.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "merchant")
@Data
@Entity
public class Merchant {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "offer")
    private BigDecimal offer;
}
