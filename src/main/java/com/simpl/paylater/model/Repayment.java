package com.simpl.paylater.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "repayment")
@Entity
public class Repayment {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "amount")
    @JsonProperty(required = true)
    private BigDecimal amount;

    @Column(name = "userId")
    private Long userId;

    @Transient
    @JsonProperty(required = true)
    private String userEmail;

}
