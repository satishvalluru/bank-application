package com.java.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long custid;
	
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String mobile;
	
	
	@OneToMany(mappedBy = "cust",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<AccountNumberEntity> acnumber;


	public long getCustid() {
		return custid;
	}


	public void setCustid(long custid) {
		this.custid = custid;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public List<AccountNumberEntity> getAcnumber() {
		return acnumber;
	}


	public void setAcnumber(List<AccountNumberEntity> acnumber) {
		this.acnumber = acnumber;
	}
}