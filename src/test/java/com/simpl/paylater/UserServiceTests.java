package com.simpl.paylater;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.model.Repayment;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.response.ApiResponse;
import com.simpl.paylater.service.MerchantService;
import com.simpl.paylater.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    MerchantService merchantService;

    @Test
    void testUserCreation(){
        ApiResponse response = createACustomer();
        assertEquals(true,response.getSuccess());
    }

    @Test
    void merchantCreation(){
        ApiResponse response = createAMerchant();
        assertEquals(true,response.getSuccess());
    }

    @Test
    void testUserTxn(){
        createACustomer();
        createAMerchant();
        ApiResponse response = createTxn();
        assertEquals(true,response.getSuccess());
    }

    @Test
    void testUserRepayment(){
        createACustomer();
        createAMerchant();
        createTxn();
        Repayment repayment = new Repayment();
        repayment.setAmount(new BigDecimal("100"));
        repayment.setUserEmail("test1@gmail.com");
        ApiResponse response = userService.pay(repayment);
        assertEquals(true,response.getSuccess());
    }

    @Test
    void testInvalidUserInput(){
        User user = new User();
        user.setName("Test1");
        user.setCreditLimit(new BigDecimal("12000"));
        ApiResponse response = userService.createOrModifyUser(user);
        assertEquals(false,response.getSuccess());
    }

    @Test
    void testInvalidMerchantInput(){
        Merchant merchant = new Merchant();
        merchant.setName("testMerchant");
        ApiResponse response = merchantService.createOrModifyMerchant(merchant);
        assertEquals(false,response.getSuccess());
    }

    @Test
    void testInvalidTxnInput(){
        Txn txn = new Txn();
        txn.setTxnAmount(new BigDecimal("200"));
        txn.setMerchantName("testMerchant");
        ApiResponse response = userService.createTxn(txn);
        assertEquals(false,response.getSuccess());
    }

    @Test
    void testInvalidRepaymentInput(){
        Repayment repayment = new Repayment();
        repayment.setAmount(new BigDecimal("100"));
        ApiResponse response = userService.pay(repayment);
        assertEquals(false,response.getSuccess());
    }

    private ApiResponse createACustomer(){
        User user = new User();
        user.setName("Test1");
        user.setEmail("test1@gmail.com");
        user.setCreditLimit(new BigDecimal("12000"));
        ApiResponse response = userService.createOrModifyUser(user);
        return response;
    }

    private ApiResponse createAMerchant(){
        Merchant merchant = new Merchant();
        merchant.setName("testMerchant");
        merchant.setOffer(new BigDecimal("1.5"));
        ApiResponse response = merchantService.createOrModifyMerchant(merchant);
        return response;
    }

    private ApiResponse createTxn(){
        Txn txn = new Txn();
        txn.setTxnAmount(new BigDecimal("200"));
        txn.setUserEmail("test1@gmail.com");
        txn.setMerchantName("testMerchant");
        ApiResponse response = userService.createTxn(txn);
        return response;
    }

}
