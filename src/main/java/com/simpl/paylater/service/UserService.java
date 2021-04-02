package com.simpl.paylater.service;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.model.Repayment;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.repository.MerchantRepository;
import com.simpl.paylater.repository.RepaymentRepository;
import com.simpl.paylater.repository.TxnRepository;
import com.simpl.paylater.repository.UserRepository;
import com.simpl.paylater.response.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TxnRepository txnRepository;

    @Autowired
    RepaymentRepository repaymentRepository;

    public ApiResponse createOrModifyUser(User user){
        ApiResponse response = new ApiResponse();
        User createdUser = null;
        if( user.getEmail() == null){
            response.setSuccess(false);
            response.setMessage("Mandatory params missing");
            return response;
        }
        createdUser = userRepository.findByEmail(user.getEmail());
        if(createdUser == null){
            if(user.getCreditLimit()== null || user.getName() == null){
                response.setSuccess(false);
                response.setMessage("Mandatory params missing");
                return response;
            }
            try {
                createdUser = userRepository.save(user);
            }
            catch (ConstraintViolationException e){
                response.setMessage("Email should be unique");
                response.setSuccess(false);
                return response;
            }
            if(createdUser!=null){
                return response;
            }
            else {
                response.setMessage("UserCreation failed");
                response.setSuccess(false);
                return response;
            }
        }
        else{
            if(user.getName()!=null)
                createdUser.setName(user.getName());
            if(user.getCreditLimit()!=null)
                createdUser.setCreditLimit(user.getCreditLimit());
            userRepository.save(createdUser);
            return response;
        }

    }


    public ApiResponse createTxn(Txn txn){
        ApiResponse response = new ApiResponse();
        User user = userRepository.findByEmail(txn.getUserEmail());
        if(user == null){
            response.setMessage("User not  found");
            response.setSuccess(false);
            return response;
        }
        Merchant merchant = merchantRepository.findByName(txn.getMerchantName());
        if(merchant!=null){
            txn.setMerchantOffer(merchant.getOffer());
            txn.setUserId(user.getId());
            txn.setMerchantId(merchant.getId());
            BigDecimal userAvailableLimit = user.getCreditLimit().subtract(user.getUsedLimit());
            if(userAvailableLimit.compareTo(BigDecimal.ZERO)<0){
                response.setMessage("User Limit reached");
                response.setSuccess(false);
                return response;
            }
            if(txn.getTxnAmount().compareTo(userAvailableLimit) > 0){
                response.setSuccess(false);
                response.setMessage("User Credit Limit reached");
                return response;
            }
            else{
                Txn savedTxn = txnRepository.save(txn);
                if(savedTxn!=null){
                    user.setUsedLimit(user.getUsedLimit().add(txn.getTxnAmount()));
                    userRepository.save(user);
                    return response;
                }
                else{
                    response.setSuccess(false);
                    response.setMessage("Transaction failed");
                    return response;
                }
            }
        }
        else {
            response.setSuccess(false);
            response.setMessage("Invalid merchant");
            return response;
        }
    }

    public ApiResponse pay(Repayment repayment){
        ApiResponse response = new ApiResponse();
        User user = userRepository.findByEmail(repayment.getUserEmail());
        if(user!=null){
            repayment.setUserId(user.getId());
            user.setUsedLimit(user.getUsedLimit().subtract(repayment.getAmount()));
            repaymentRepository.save(repayment);
            userRepository.save(user);
            return response;
        }
        else{
            response.setSuccess(false);
            response.setMessage("User not found");
            return response;
        }
    }
}
