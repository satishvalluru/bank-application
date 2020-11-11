package com.java.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactionhistory")
public class TransactionMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transid;

	private long frmaccount;

	private long toaccount;

	private Double amount;

	private String remarks;

	private Date transdate;

	public long getTransid() {
		return transid;
	}

	public void setTransid(long transid) {
		this.transid = transid;
	}

	public long getFrmaccount() {
		return frmaccount;
	}

	public void setFrmaccount(long frmaccount) {
		this.frmaccount = frmaccount;
	}

	public long getToaccount() {
		return toaccount;
	}

	public void setToaccount(long toaccount) {
		this.toaccount = toaccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getTransdate() {
		return transdate;
	}

	public void setTransdate(Date transdate) {
		this.transdate = transdate;
	}

}