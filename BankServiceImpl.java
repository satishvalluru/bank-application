package com.java.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.dto.TransactionMasterRequest;
import com.java.exception.FromAccountNotFoundException;
import com.java.exception.InsufficientAmountException;
import com.java.exception.CustomerNotFoundException;
import com.java.model.AccountNumberEntity;
import com.java.model.Customer;
import com.java.model.TransactionMaster;
import com.java.repository.AccountNumberRepository;
import com.java.repository.BankAccountRepository;
import com.java.repository.TransRepository;
import com.java.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	BankAccountRepository customerRepository;

	@Autowired
	AccountNumberRepository accRepository;

	@Autowired
	TransRepository transRepository;

	@Override
	public Customer CreateOrUpdateCustomer(Customer customer) throws CustomerNotFoundException {
		Optional<Customer> custById = customerRepository.findById(customer.getCustid());
		if(custById.isPresent()) {
			throw new CustomerNotFoundException("user not found");
		}
		System.out.println("Customer availability check:  " + (custById.isPresent()));
		if (custById.isPresent()) {
			Customer newcustomer = custById.get();
			newcustomer.setFirstname(customer.getFirstname());
			newcustomer.setLastname(customer.getLastname());
			newcustomer.setEmail(customer.getEmail());
			newcustomer.setMobile(customer.getMobile());
			newcustomer = customerRepository.save(newcustomer);
			return newcustomer;
		} else {
			Optional<AccountNumberEntity> acNumb = accRepository.findById(customer.getCustid());
			if (acNumb.isPresent()) {
				AccountNumberEntity acn = acNumb.get();
				acn.setAcno(generateaccountNumber());
				acn.setOpeningbalance(5000.00);
				return customer;
			} else {
				AccountNumberEntity ac = acNumb.get();
				ac.setAcno(generateaccountNumber());
				ac.setOpeningbalance(5000.00);
				customer = customerRepository.save(customer);
				return customer;
			}
		}
	}

	@Override
	@Transactional
	public TransactionMasterRequest fundTransfer(TransactionMasterRequest transreq) throws Exception {
		Optional<AccountNumberEntity> fromac = accRepository.findByAccNumber(transreq.getFromAccount());
		if (fromac.isPresent()) {
			Optional<AccountNumberEntity> toac = accRepository.findByAccNumber(transreq.getToAccount());
			if (toac.isPresent()) {
				AccountNumberEntity updatefrmamount = fromac.get();
				AccountNumberEntity updateToamount = toac.get();
				if (updatefrmamount.getCurrentbalance() >= transreq.getAmount()) {
					//acn.stream().forEach(strcaard->baccount.setAcno(strcaard.getAcno()));
					//acn.stream().forEach(strcaard->baccount.setBalance(strcaard.getBalance()-amount));
					updatefrmamount.setCurrentbalance((updatefrmamount.getCurrentbalance() - transreq.getAmount()));
					accRepository.save(updatefrmamount);
	
					updateToamount.setCurrentbalance((updateToamount.getCurrentbalance() + transreq.getAmount()));
	
					accRepository.save(updateToamount);
	
					TransactionMaster transmaster = new TransactionMaster();
					// transmaster.setTransid(transreq.getTransid());
					transmaster.setAmount(transreq.getAmount());
					transmaster.setFrmaccount(transreq.getFromAccount());
					transmaster.setToaccount(transreq.getToAccount());
	
					transmaster.setTransdate(Calendar.getInstance().getTime());
					transmaster.setRemarks(transreq.getRemarks());
					transmaster = transRepository.save(transmaster);
					return transreq;
				} else {
					throw new InsufficientAmountException("Insufficiant Amount !!!!");
				}
	
			} else {
				throw new InsufficientAmountException("To Account Must be Register accountNumber Only!!");
			}
		} else {
			throw new InsufficientAmountException("From Account Number Not Found");
		}
	
	}

	/*
	 * else { throw new UserAccountNotFoundException("Insufficiant Amount !!!!"); }
	 * 
	 * } else { throw new
	 * UserAccountNotFoundException("To Account Must be Register accountNumber Only!!"
	 * ); } } else { throw new
	 * UserAccountNotFoundException("From Account Number Not Found"); }
	 */

	@Override
	public List<TransactionMaster> getMiniStatement(long acno, String trnsdate) throws ParseException {
		// String sDate1=transreq.getTransdate();
		java.util.Date transdate = null;
		transdate = new SimpleDateFormat("dd-MM-yyyy").parse(trnsdate);
		List<TransactionMaster> transList = transRepository.getTransDataByacNoAndDate(acno, transdate);
		if (transList.size() > 0) {
			return transList;
		} else {
			return new ArrayList<TransactionMaster>();
		}
	}

	public static long generateaccountNumber() {// int length
		Random random = new Random();
		char[] digits = new char[12];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < 12; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		System.out.println("Digits: " + Long.parseLong(new String(digits)));
		return Long.parseLong(new String(digits));
	}

	@Transactional
	@Override
	public List<TransactionMaster> getstatement(long accountnumber) throws FromAccountNotFoundException {
		Pageable top10 = PageRequest.of(0, 10, Sort.by(Direction.DESC, "transdate"));
		List<TransactionMaster> stmt = transRepository.findstmtById(accountnumber, top10);
		if(stmt.isEmpty()) {
			throw new FromAccountNotFoundException("from account number not found");
		}
		if (stmt.size() == 0) {
			throw new FromAccountNotFoundException("Transaction not available ");
		}
		return stmt;
	}

}
