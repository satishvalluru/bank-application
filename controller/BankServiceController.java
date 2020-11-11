package com.java.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.TransactionMasterRequest;
import com.java.exception.FromAccountNotFoundException;
import com.java.exception.CustomerNotFoundException;
import com.java.model.Customer;
import com.java.model.TransactionMaster;
import com.java.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankServiceController {

	@Autowired
	BankService bankservice;

	@Autowired
	Environment environment;

	@GetMapping("/port")
	public String getInfo() {
		String port = environment.getProperty("local.server.port");
		return "from server" + port;
	}

	@GetMapping("/{acno}/{transdate}")
	public ResponseEntity<List<TransactionMaster>> getMiniStatement(@PathVariable("acno") long acno,
			@PathVariable("transdate") String transdate) throws ParseException {
		List<TransactionMaster> statement = bankservice.getMiniStatement(acno, transdate);
		return new ResponseEntity<List<TransactionMaster>>(statement, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/createorupdateCustomer")
	public ResponseEntity<?> CreateOrUpdateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
		Customer updatecustomer = bankservice.CreateOrUpdateCustomer(customer);
		return new ResponseEntity<Customer>(updatecustomer, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/fundtransfer")
	public ResponseEntity<String> fundTransfer(@RequestBody TransactionMasterRequest transrequest)
			throws Exception {
		bankservice.fundTransfer(transrequest);
		return new ResponseEntity<String>("fund transfer successfully", new HttpHeaders(), HttpStatus.OK);
	}

	/*
	 * @PostMapping("/payment") //@RequestParam("amount") public
	 * ResponseEntity<TransactionMaster> paymentofOrder(@RequestParam("cid") long
	 * cid,@RequestParam("amount") Double amount) throws Exception{ //public
	 * ResponseEntity<TransactionHistory> paymentofOrder(@RequestBody
	 * TransactionHistory transhistory) throws Exception{
	 * System.out.println("CID: "+cid+"\t amount: "+amount); TransactionHistory
	 * transdata=bservice.makepayment(cid,amount); return new
	 * ResponseEntity<TransactionHistory>(transdata,new HttpHeaders(),
	 * HttpStatus.OK); }
	 */

	@GetMapping("/lateststmt/{accountnumber}")
	public ResponseEntity<List<TransactionMaster>> latest10records(@PathVariable("accountnumber") int accountnumber) throws FromAccountNotFoundException {
		List<TransactionMaster> top10records = bankservice.getstatement(accountnumber);
		return new ResponseEntity<List<TransactionMaster>>(top10records, new HttpHeaders(), HttpStatus.OK);
	}

}
