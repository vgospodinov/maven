package com.initech.ini.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "eshop_order")
@SequenceGenerator(
	name           = "ESHOP_ORDER_ID_SEQ", 
	sequenceName   = "ESHOP_ORDER_ID_SEQ",
	initialValue   = 1,
	allocationSize = 1)
public class EShopOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Database primary key. Will be assigned automaticly for both Oracle and
	 * mySQL databases.
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(
		strategy = GenerationType.AUTO, 
		generator = "ESHOP_ORDER_ID_SEQ")
	private long id;
	
	
	/**
	 * Identifier coming from eshop
	 */
	@Column(
		name = "eshop_order_no", 
		nullable = false,
		length = 20,
		insertable = true,
		updatable = false, 
		unique = true)
	private String eshopOrderNo;
	
	
	@Column(name = "import_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateImported;

	
	
	
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getEshopOrderNo() {
		return eshopOrderNo;
	}


	public void setEshopOrderNo(String eshopOrderId) {
		this.eshopOrderNo = eshopOrderId;
	}


	public Date getDateImported() {
		return dateImported;
	}


	public void setDateImported(Date dateImported) {
		this.dateImported = dateImported;
	}
	
	
}

