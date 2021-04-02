package com.simpl.paylater.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
public class UserDueResponse extends ApiResponse {
    private HashMap<String, BigDecimal> dues;
}
