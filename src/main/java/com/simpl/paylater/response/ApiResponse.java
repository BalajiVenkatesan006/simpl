package com.simpl.paylater.response;

import lombok.Data;

@Data
public class ApiResponse {
    private Boolean success = Boolean.TRUE;
    private String message;
}
