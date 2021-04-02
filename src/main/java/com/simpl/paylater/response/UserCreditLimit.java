package com.simpl.paylater.response;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class UserCreditLimit extends ApiResponse{
    private List<String> users;
}
