package com.java.service;

import java.text.ParseException;
import java.util.List;

import com.java.dto.TransactionMasterRequest;
import com.java.exception.FromAccountNotFoundException;
import com.java.exception.CustomerNotFoundException;
import com.java.model.Customer;
import com.java.model.TransactionMaster;

public interface BankService {
	
	public Customer CreateOrUpdateCustomer(Customer customer) throws CustomerNotFoundException;

    public TransactionMasterRequest fundTransfer(TransactionMasterRequest transrequest) throws Exception;

	public List<TransactionMaster> getMiniStatement(long acno, String transdate) throws ParseException;

	List<TransactionMaster> getstatement(long accountnumber) throws FromAccountNotFoundException;

	//TransactionMaster fundTransfer(long cid, Double amount) throws Exception;

}
