package com.billpin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billpin.models.BaseResponse;
import com.billpin.models.Customer;

import com.billpin.repositories.CustomerRepo;
import com.billpin.reqs.SigninReq;
import com.billpin.reqs.SignupReq;
import com.billpin.resps.LoginResp;
import com.billpin.utilfunctions.Constants;
import com.billpin.utilfunctions.MongoDBSequenceDAO;


@Controller
public class LoginController {


	@Autowired
	private CustomerRepo customerrepo;

	@Autowired
	private MongoDBSequenceDAO seqDao;


	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/")
	public String mainpage() {

		return "index.html";
	}


	@RequestMapping(value = "/signup" ,method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> signup(@RequestBody SignupReq req ) {

		BaseResponse br = new BaseResponse();
		Customer customer = customerrepo.findByCustomerEmail(req.getCustomerEmail());
		System.out.println("signup form");
		System.out.println("hi"+req.getCustomerEmail());
		try {
			if(customer == null && req.getCustomerEmail().length() > 0) {
				customer = new Customer();
				BeanUtils.copyProperties(req, customer);	
				customer.setCustID(seqDao.getNextSequece(Constants.CUSTOMER_ID));;
				customerrepo.save(customer);
				br.setMessage("success");
				br.setResponseCode(200);;

			}else {
				br.setMessage("EmailID already Exists ");
				br.setResponseCode(200);
			}
		}catch(Exception e) {
			LOGGER.info("Exception in signup");
		}
		return ResponseEntity.status(HttpStatus.OK).body(br);
	}



	@RequestMapping(value = "/signin" ,method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> signin(@RequestBody SigninReq req ) {

		LoginResp br = new LoginResp();
		System.out.println(req.getCustomerEmail()+""+req.getCustomerPassword());
		
		Customer customerExist = customerrepo.findByCustomerEmail(req.getCustomerEmail());
		
		
		try {
			Customer customer = customerrepo.findByCustomerEmailAndCustomerPassword(req.getCustomerEmail(), req.getCustomerPassword());
			//System.out.println("entered");
			if(customer == null) {

				//System.out.println("entered");
				
				if(customerExist!=null) {
					br.setMessage("Enter password");
					br.setResponseCode(200);;
				}else {
					br.setMessage("Customer not available");
					br.setResponseCode(200);;
				}
				
				

			}else {
				br.setUserId(customer.getCustID());
				br.setMessage("login success");
				br.setResponseCode(200);
			}
		}catch(Exception e) {
			LOGGER.info("Exception in sign in");
		}
		return ResponseEntity.status(HttpStatus.OK).body(br);
	}

}
