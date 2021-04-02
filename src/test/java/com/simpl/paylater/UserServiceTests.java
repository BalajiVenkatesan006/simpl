package com.simpl.paylater;

import com.simpl.paylater.model.User;
import com.simpl.paylater.response.ApiResponse;
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

    @Test
    void testUserCreation(){
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setCreditLimit(new BigDecimal("1200"));
        ApiResponse response = userService.createOrModifyUser(user);
        assertEquals(true,response.getSuccess());
    }

    @Test
    void testUserTxn(){

    }

    @Test
    void testUserRepayment(){

    }
}
