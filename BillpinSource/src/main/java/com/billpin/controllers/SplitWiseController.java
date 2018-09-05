package com.billpin.controllers;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.internal.matchers.InstanceOf.VarArgAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.billpin.models.BaseResponse;
import com.billpin.models.Customer;
import com.billpin.models.Expenses;
import com.billpin.repositories.CustomerRepo;
import com.billpin.repositories.ExpensesRepo;
import com.billpin.reqs.AddFriendsReq;
import com.billpin.reqs.ExpensesTxn;
import com.billpin.resps.ExpenseList;
import com.billpin.resps.ExpenseResp;
import com.billpin.resps.Findfrnds;
import com.billpin.resps.ListFrnds;
import com.billpin.resps.UserFrndsList;
import com.billpin.utilfunctions.Constants;
import com.billpin.utilfunctions.MongoDBSequenceDAO;


@RestController
@CrossOrigin("*")
public class SplitWiseController {

	@Autowired
	private CustomerRepo customerrepo;
	@Autowired
	private ExpensesRepo expensesrepo;
	@Autowired
	private MongoDBSequenceDAO seqDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(SplitWiseController.class);

	@RequestMapping(value = "/addingFriends" , method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> addfriends(@RequestBody AddFriendsReq req) {
		Customer customer = customerrepo.findByCustID(Long.parseLong(req.getUsername()));
		Customer customerfrnd = customerrepo.findByCustomerName(req.getFrndsname());
		BaseResponse br = new BaseResponse();
		try {
			if(customer!=null) {
				if(customerfrnd!=null) {
					List<String>  friends = customer.getFriendsList();
					boolean flag = true;
					if(friends!=null) {
						String friendflag = friends.stream().filter(var -> customerfrnd.getCustomerEmail().equals(var)).findAny().orElse(null);
						if(friendflag != null) {
							flag = false;
						}
					}

					if(flag) {
						String id = customer.get_id();
						if(friends == null) {
							friends = new ArrayList();
						}else {
							friends = customer.getFriendsList();
						}

						friends.add(customerfrnd.getCustomerEmail());
						customer.setFriendsList(friends);
						customerrepo.save(customer);
						br.setMessage("Friend Added");
						br.setResponseCode(200);

					}else {
						br.setMessage("Already Friend exists");
						br.setResponseCode(200);
					}
				}else {
					br.setMessage("customerFriend not available");
					br.setResponseCode(403);
				}

			}else {
				br.setMessage("customer not available");
				br.setResponseCode(403);
			}
		}catch(Exception e) {
			LOGGER.info("Exception in addfriends");
		}
		return ResponseEntity.status(HttpStatus.OK).body(br);
	}

	@RequestMapping(value = "/Addexpenses" , method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> expenses(@RequestBody ExpensesTxn req) {

		BaseResponse br = new BaseResponse();

		try {

			Customer customer = customerrepo.findByCustID(Long.parseLong(req.getUserid()));


			if(customer!= null) {
				LOGGER.info("customer available");
				LOGGER.info(req.getAmount());
				System.out.println(req.getFrndsIDs());
				if(Double.parseDouble(req.getAmount())>0 && req.getFrndsIDs()!= null) {
					Double totalamount = Double.parseDouble(req.getAmount());
					Double singleamount = Double.parseDouble(req.getAmount())/req.getFrndsIDs().size();
					List<String> frnds = req.getFrndsIDs();
					LOGGER.info("requests are correct");
					boolean flag = true;
					for (String string : frnds) {
						List<String>  friendsInlist = customer.getFriendsList();
						String friendflag = friendsInlist.stream().filter(var -> !string.equals(var)).findAny().orElse(null);

						if(friendflag == null) {
							flag =false;
							break;
						}

					}
					if(flag) {
						for (String string : frnds) {

							Customer user_frnd = customerrepo.findByCustomerEmail(string);
							Expenses expense = new Expenses();
							expense.setTxnID(seqDao.getNextSequece(Constants.EXPENSES_ID));
							expense.setUser(Long.parseLong(req.getUserid()));
							expense.setFriendsID(user_frnd.getCustID());
							expense.setFriendsName(user_frnd.getCustomerName());
							expense.setIndividualAmount(singleamount);
							expense.setUserAmount(totalamount);
							expense.setPaid("no");
							expense.setDate(System.currentTimeMillis());
							expensesrepo.save(expense);

						}

						br.setMessage("success");
						br.setResponseCode(200);
					}else {
						br.setMessage("Friends not available in list");
						br.setResponseCode(200);
					}
				}else {
					br.setMessage("amount or choose friends correctly");
					br.setResponseCode(403);
				}	
			}else {
				br.setMessage("UserID not available");
				br.setResponseCode(403);
			}
		}catch(Exception e) {
			LOGGER.info("Exception in expenses");
		}


		return ResponseEntity.status(HttpStatus.OK).body(br);
	}


	@RequestMapping(value = "/userInvolvetransactions/{usermail}" , method = RequestMethod.POST)
	public ResponseEntity<ExpenseList> userInvolvetransactions(@PathVariable("usermail") String usermail) {

		ExpenseList resp = new ExpenseList();
		try {
			Customer cust_frnd = customerrepo.findByCustomerEmail(usermail);
			List<Expenses> userExpense = expensesrepo.findAllByFriendsID(cust_frnd.getCustID());

			List<ExpenseResp> txns_list = new ArrayList<ExpenseResp>();


			if(userExpense!= null) {

				for(Expenses singletxn : userExpense) {
					ExpenseResp txn = new ExpenseResp();
					Customer cust = customerrepo.findByCustID(singletxn.getUser());
					txn.setFriendName(cust.getCustomerName());
					txn.setFrndID(singletxn.getUser());
					txn.setUserid(cust_frnd.getCustID());
					txn.setTotalAmount(singletxn.getUserAmount());
					txn.setYouOwe(singletxn.getIndividualAmount());
					txns_list.add(txn);
				}
				if(txns_list.size()>0) {
					resp.setTxns_list(txns_list);
					resp.setMessage("success");
					resp.setResponseCode(200);
				}else {

					resp.setMessage("No transactions");
					resp.setResponseCode(200);
				}

			}else {
				resp.setMessage("Expenses not available");
				resp.setResponseCode(200);
			}


		}catch(Exception e) {
			LOGGER.info("Exception in usertransactions");
		}

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}


	@RequestMapping(value = "/userOwnTransactions/{userid}" , method = RequestMethod.POST)
	public ResponseEntity<ExpenseList> userOwntransactions(@PathVariable("userid") String userid) {

		ExpenseList resp = new ExpenseList();
		try {
			List<Expenses> userExpense = expensesrepo.findAllByUser(Long.parseLong(userid));

			List<ExpenseResp> txns_list = new ArrayList<ExpenseResp>();


			if(userExpense!= null) {

				for(Expenses singletxn : userExpense) {
					ExpenseResp txn = new ExpenseResp();
					txn.setFriendName(singletxn.getFriendsName());
					txn.setFrndID(singletxn.getFriendsID());
					txn.setUserid(Long.parseLong(userid));
					txn.setTotalAmount(singletxn.getUserAmount());
					txn.setYouOwe(singletxn.getIndividualAmount());
					txns_list.add(txn);
				}
				resp.setTxns_list(txns_list);
				resp.setMessage("success");
				resp.setResponseCode(200);
			}else {
				resp.setMessage("Expenses not available");
				resp.setResponseCode(200);
			}


		}catch(Exception e) {
			LOGGER.info("Exception in usertransactions");
		}

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	@RequestMapping(value = "/frndsname/{name}" , method = RequestMethod.POST)
	public ResponseEntity<ListFrnds> searchfrnds(@PathVariable("name") String name) {


		List<Customer> customers = customerrepo.findAll();
		ListFrnds resp = new ListFrnds();
		if(name.length() > 0) {
			List<Findfrnds> friendslist = new ArrayList<Findfrnds>();
			String frndname = name.toLowerCase().trim();
			System.out.println("hi");
			System.out.println(frndname);
			List<Long>  frndsIDs= customers.stream().filter(var->var.getCustomerEmail().toLowerCase().contains(frndname)).map(var->var.getCustID()).collect(Collectors.toList());
			if(frndsIDs.size() > 0) {
				for (Long long1 : frndsIDs) {
					Findfrnds frnds = new Findfrnds();

					Customer cust = customerrepo.findByCustID(long1);
					frnds.setFrndID(cust.getCustID());
					frnds.setName(cust.getCustomerName());
					friendslist.add(frnds);

				}
				resp.setMessage("success");
				resp.setResponseCode(200);
				resp.setFriends(friendslist);
			}else {
				resp.setMessage("No friends");
				resp.setResponseCode(403);
			}

		}else {
			resp.setMessage("Type Friend Name");
			resp.setResponseCode(403);
		}
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@RequestMapping(value = "/friendslist/{userid}" , method = RequestMethod.POST)
	public ResponseEntity<UserFrndsList> getFriendsList(@PathVariable("userid") String userid) {

		Customer cust = customerrepo.findByCustID(Long.parseLong(userid));
		UserFrndsList resp = new UserFrndsList();
		if(cust.getFriendsList()!=null) {
			if(cust.getFriendsList().size()>0) {
				resp.setFriendsList(cust.getFriendsList());
				resp.setMessage("success");
				resp.setResponseCode(200);
			}else {
				resp.setMessage("friends not available");
				resp.setResponseCode(200);
			}
		}else {
			resp.setMessage("No friends");
			resp.setResponseCode(403);
		}
		return ResponseEntity.status(HttpStatus.OK).body(resp);


	}


}
