package com.simpl.paylater.service;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.repository.MerchantRepository;
import com.simpl.paylater.repository.TxnRepository;
import com.simpl.paylater.repository.UserRepository;
import com.simpl.paylater.response.MerchantDiscount;
import com.simpl.paylater.response.UserCreditLimit;
import com.simpl.paylater.response.UserDueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TxnRepository txnRepository;

    public UserDueResponse getUserDue(String email){
        UserDueResponse userDueResponse = new UserDueResponse();
        User user = userRepository.findByEmail(email);
        if(user!=null){
            HashMap<String , BigDecimal> due = new HashMap<>();
            due.put(user.getEmail(),user.getUsedLimit());
            userDueResponse.setDues(due);
            return userDueResponse;
        }
        userDueResponse.setSuccess(false);
        userDueResponse.setMessage("User not found");
        return userDueResponse;
    }

    public UserDueResponse getTotalDues(){
        List<User> users = userRepository.findAll();
        HashMap<String,BigDecimal> due  = new HashMap<>();
        for(User u :users){
            due.put(u.getEmail(),u.getUsedLimit());
        }
        UserDueResponse userDueResponse = new UserDueResponse();
        userDueResponse.setDues(due);
        return userDueResponse;
    }

    public UserCreditLimit getCreditLimitUsers(){
        List<User> users = userRepository.findAll();
        List<String> creditUsedUsers = new ArrayList<>();
        for(User u : users){
            if(u.getUsedLimit().compareTo(u.getCreditLimit())>=0){
                creditUsedUsers.add(u.getEmail());
            }
        }
        UserCreditLimit userCreditLimit = new UserCreditLimit();
        userCreditLimit.setUsers(creditUsedUsers);
        return userCreditLimit;
    }

    public MerchantDiscount getDiscounts(String merchantName){
        Merchant merchant = merchantRepository.findByName(merchantName);
        MerchantDiscount merchantDiscount = new MerchantDiscount();
        if(merchant!=null){
            List<Txn> txnList = txnRepository.findByMerchantId(merchant.getId());
            BigDecimal resultDiscount = BigDecimal.ZERO;
            for(Txn t: txnList){
                BigDecimal percentAmount = t.getTxnAmount().multiply(t.getMerchantOffer()).divide(new BigDecimal("100"));
                resultDiscount = resultDiscount.add(percentAmount);
            }
            merchantDiscount.setDiscount(resultDiscount);
            return merchantDiscount;
        }
        merchantDiscount.setSuccess(false);
        merchantDiscount.setMessage("Merchant not found");
        return merchantDiscount;
    }
}
