package com.tus.nms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "mcc_mnc_details")
public class MccMncDetails {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "mcc")
    private Integer mcc;

    @Column(name = "mnc")
    private Integer mnc;

    @Column(name = "country")
    private String country;

    @Column(name = "operator")
    private String operator;

    // Getters and Setters
    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public Integer getMnc() {
        return mnc;
    }

    public void setMnc(Integer mnc) {
        this.mnc = mnc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    // Default Constructor
    public MccMncDetails() {
    }

	public MccMncDetails(Long id, Integer mcc, Integer mnc, String country, String operator) {
		this.id = id;
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "MccMncDetails [id=" + id + ", mcc=" + mcc + ", mnc=" + mnc + ", country=" + country + ", operator="
				+ operator + "]";
	}
}

