package com.simpl.paylater.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantDiscount extends ApiResponse {
    private BigDecimal discount;
}
