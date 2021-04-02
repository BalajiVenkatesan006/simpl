package com.simpl.paylater.service;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.repository.MerchantRepository;
import com.simpl.paylater.response.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    public ApiResponse createOrModifyMerchant(Merchant merchant){
        ApiResponse apiResponse = new ApiResponse();
        Merchant createdMerchant = null;
        if(merchant.getName() == null){
            apiResponse.setMessage("Mandatory params missing");
            apiResponse.setSuccess(false);
            return apiResponse;
        }
        createdMerchant = merchantRepository.findByName(merchant.getName());
        if(createdMerchant == null ){
            if(merchant.getOffer() == null){
                apiResponse.setMessage("Mandatory params missing");
                apiResponse.setSuccess(false);
                return apiResponse;
            }
            try{
                createdMerchant = merchantRepository.save(merchant);
            }
            catch (ConstraintViolationException e){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Merchant Name should be unique");
                return apiResponse;
            }
            if(createdMerchant!=null){
                return apiResponse;
            }
            else{
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Merchant creation failed");
                return apiResponse;
            }
        }
        else{
            if(merchant.getOffer()!=null)
                createdMerchant.setOffer(merchant.getOffer());
            merchantRepository.save(createdMerchant);
            return apiResponse;
        }

    }
}
