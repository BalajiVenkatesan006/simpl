package com.simpl.paylater.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Txn")
public class Txn {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty(required = true)
    @Column(name = "txnAmount")
    private BigDecimal txnAmount;

    @JsonProperty(required = true)
    @Transient
    private String merchantName;

    @Column(name = "merchantId")
    private Long merchantId;

    @Column(name = "merchantOffer")
    private BigDecimal merchantOffer;

    @Column(name = "userId")
    private Long userId;

    @Transient
    @JsonProperty(required = true)
    private String userEmail;
}
