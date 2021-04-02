package com.simpl.paylater.controller;

import com.simpl.paylater.model.Repayment;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.response.ApiResponse;
import com.simpl.paylater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public @ResponseBody
    ApiResponse createUser(@RequestBody User user){
        return userService.createOrModifyUser(user);
    }

    @PutMapping("/edit")
    public @ResponseBody ApiResponse editUser(@RequestBody User user){
        return userService.createOrModifyUser(user);
    }

    @PostMapping("/txn")
    public @ResponseBody ApiResponse pay(@RequestBody Txn txn){
        return userService.createTxn(txn);
    }


    @PostMapping("/pay")
    public @ResponseBody ApiResponse payback(@RequestBody Repayment repayment){
        return userService.pay(repayment);
    }
}
