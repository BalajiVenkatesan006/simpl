package com.simpl.paylater.controller;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.repository.MerchantRepository;
import com.simpl.paylater.repository.TxnRepository;
import com.simpl.paylater.repository.UserRepository;
import com.simpl.paylater.response.MerchantDiscount;
import com.simpl.paylater.response.UserCreditLimit;
import com.simpl.paylater.response.UserDueResponse;
import com.simpl.paylater.service.MerchantService;
import com.simpl.paylater.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TxnRepository txnRepository;

    @Autowired
    ReportService reportService;

    @GetMapping("/userDue")
    public @ResponseBody
    UserDueResponse getUserDue(@RequestParam String email){
       return reportService.getUserDue(email);
    }

    @GetMapping("/totalDue")
    public @ResponseBody UserDueResponse getTotalDues(){
        return reportService.getTotalDues();
    }

    @GetMapping("/creditLimit")
    public @ResponseBody
    UserCreditLimit getCreditLimitCustomers(){
        return reportService.getCreditLimitUsers();
    }

    @GetMapping("/merchantDiscount")
    public @ResponseBody
    MerchantDiscount getMerchantDiscount(@RequestParam String merchantName){
        return reportService.getDiscounts(merchantName);
    }
}
