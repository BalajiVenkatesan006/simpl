package com.simpl.paylater;

import com.simpl.paylater.model.Merchant;
import com.simpl.paylater.model.Repayment;
import com.simpl.paylater.model.Txn;
import com.simpl.paylater.model.User;
import com.simpl.paylater.response.ApiResponse;
import com.simpl.paylater.response.MerchantDiscount;
import com.simpl.paylater.response.UserCreditLimit;
import com.simpl.paylater.response.UserDueResponse;
import com.simpl.paylater.service.MerchantService;
import com.simpl.paylater.service.ReportService;
import com.simpl.paylater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = "com.simpl.paylater")
@ComponentScan(basePackages = "com.simpl.paylater")
@EnableJpaRepositories
public class PaylaterApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Autowired
	MerchantService merchantService;

	@Autowired
	ReportService reportService;

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(PaylaterApplication.class, args);
	}

	@Override

	public void run(String... args) {
		if(!env.acceptsProfiles(Profiles.of("test"))) {
			handleCommandLoop();
		}
	}

	private void handleCommandLoop(){
		String command = "run";
		Scanner input = new Scanner(System.in);
		System.out.println("==============================");
		System.out.println("You can Start entering the commands");
		System.out.println("==============================");
		do{
			System.out.println("Please Enter the command");
			command = input.nextLine();
			String c[] = command.split(" ");
			try{
				switch (c[0]){
					case "new":
						if(c[1].equals("txn")){
							Txn txn = new Txn();
							txn.setMerchantName(c[3]);
							txn.setUserEmail(c[2]);
							txn.setTxnAmount(new BigDecimal(c[4]));
							ApiResponse response = userService.createTxn(txn);
							System.out.println("Success " +response.getSuccess()+"  "+response.getMessage());
						}
					case "update":
						if(c[1].equals("user")){
							User user = new User();
							user.setName(c[2]);
							user.setEmail(c[3]);
							user.setCreditLimit(new BigDecimal(c[4]));
							ApiResponse response = userService.createOrModifyUser(user);
							System.out.println("Success " +response.getSuccess()+"  "+response.getMessage());
						}
						if(c[1].equals("merchant")){
							Merchant merchant = new Merchant();
							merchant.setName(c[2]);
							merchant.setOffer(new BigDecimal(c[3]));
							ApiResponse response = merchantService.createOrModifyMerchant(merchant);
							System.out.println("Success " +response.getSuccess()+"  "+response.getMessage());
						}
						break;
					case "payback":
						Repayment repayment = new Repayment();
						repayment.setUserEmail(c[1]);
						repayment.setAmount(new BigDecimal(c[2]));
						ApiResponse response = userService.pay(repayment);
						System.out.println("Success " +response.getSuccess()+"  "+response.getMessage());
						break;
					case "report":
						if(c[1].equals("discount")){
							MerchantDiscount merchantDiscount = reportService.getDiscounts(c[2]);
							System.out.println("Success " +merchantDiscount.getSuccess()+"  "+merchantDiscount.getMessage()+" "+merchantDiscount.getDiscount());
						}
						if(c[1].equals("dues")){
							UserDueResponse userDueResponse = reportService.getUserDue(c[2]);
							System.out.println("Success " +userDueResponse.getSuccess()+" "+userDueResponse.getMessage());
							for(Map.Entry<String,BigDecimal> m : userDueResponse.getDues().entrySet()){
								System.out.println(m.getKey()+" "+m.getValue());
							}
						}
						if(c[1].equals("users-at-credit-limit")){
							UserCreditLimit userCreditLimit = reportService.getCreditLimitUsers();
							System.out.println("Success " +userCreditLimit.getSuccess()+" "+userCreditLimit.getMessage());
							for(String m : userCreditLimit.getUsers()){
								System.out.println(m);
							}
						}
						if(c[1].equals("total-dues")){
							UserDueResponse userDueResponse = reportService.getTotalDues();
							System.out.println("Success " +userDueResponse.getSuccess()+" "+userDueResponse.getMessage());
							for(Map.Entry<String,BigDecimal> m : userDueResponse.getDues().entrySet()){
								System.out.println(m.getKey()+" "+m.getValue());
							}
						}
						break;
				}
			}
			catch (Exception e){
				System.out.println(" Invalid Input!!!!!!!!");
			}
		}
		while (!command.equals("exit"));
		System.out.println("==============================");
		System.out.println("Logging out");
		System.out.println("==============================");
	}

}
