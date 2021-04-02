package com.simpl.paylater.controller;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.response.ApiResponse;
import com.simpl.paylater.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @PostMapping("/create")
    public @ResponseBody
    ApiResponse createMerchant(@RequestBody Merchant merchant){
        return merchantService.createOrModifyMerchant(merchant);
    }

    @PutMapping("/edit")
    public @ResponseBody ApiResponse editMerchant(@RequestBody Merchant merchant){
        return merchantService.createOrModifyMerchant(merchant);
    }
}
